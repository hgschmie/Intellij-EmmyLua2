#!/bin/bash
set -euo pipefail
PLUGIN_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
REPOSITORY_ROOT="$(dirname "$(git -C "$PLUGIN_ROOT" rev-parse --path-format=absolute --git-common-dir)")"
WORKSPACE="$(cd "$REPOSITORY_ROOT/../.." && pwd)"
ANALYZER="${FMTK_ANALYZER_PATH:-$WORKSPACE/upstream/emmylua-analyzer-rust/build/checkouts/intellij-work}"
if [ "$(uname -m)" != arm64 ]; then
  echo 'This development package builds the patched server for macOS arm64 only.' >&2
  exit 1
fi
if [ "$(git -C "$ANALYZER" rev-parse HEAD)" != "$(cat "$PLUGIN_ROOT/analyzer.lock")" ]; then
  echo 'Analyzer revision differs from analyzer.lock; review the pin before building.' >&2
  exit 1
fi
if ! git -C "$ANALYZER" diff --quiet HEAD --; then
  echo 'Analyzer has tracked changes; commit and review its pin before building.' >&2
  exit 1
fi
mkdir -p "$PLUGIN_ROOT/build/logs"
exec > >(tee "$PLUGIN_ROOT/build/logs/build-module-patch-$(date +%Y%m%d-%H%M%S)-$$.log") 2>&1
export CARGO_HOME="${CARGO_HOME:-$WORKSPACE/dev/cache/cargo}"
export CARGO_TARGET_DIR="${CARGO_TARGET_DIR:-$ANALYZER/target}"
export TMPDIR="$WORKSPACE/dev/tmp"
mkdir -p "$CARGO_HOME" "$TMPDIR" "$WORKSPACE/dev/build-home"
cargo build --locked --release --manifest-path "$ANALYZER/Cargo.toml" -p emmylua_ls
export RUNNER_NUMBER=162
export GRADLE_USER_HOME="$WORKSPACE/dev/cache/gradle"
export npm_config_cache="$WORKSPACE/dev/cache/npm"
export JAVA_HOME="${FMTK_JAVA_HOME:-/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home}"
export JAVA_TOOL_OPTIONS="-Duser.home=$WORKSPACE/dev/build-home -Djava.io.tmpdir=$TMPDIR"
cd "$PLUGIN_ROOT"
bash gradlew --no-daemon --no-configuration-cache \
  "-PlocalPlatformPath=${FMTK_IDEA_PATH:-/Users/henning/Applications/IntelliJ IDEA.app}" \
  "-PlocalLsp4ijPath=$WORKSPACE/dev/plugins/lsp4ij" \
  "-PlocalAnalyzerPath=$CARGO_TARGET_DIR/release/emmylua_ls" buildPlugin
python3 - "$PLUGIN_ROOT" "$WORKSPACE" "$CARGO_TARGET_DIR/release/emmylua_ls" <<'PY'
from pathlib import Path
import hashlib, io, shutil, subprocess, sys, tempfile, zipfile
import xml.etree.ElementTree as ET

plugin, workspace, binary = map(Path, sys.argv[1:])
version = subprocess.check_output([str(binary), '--version'], text=True).strip().split()[-1]
package_version = f'{version}-162-IDEA262-patched-modules'
archive = plugin / f'build/distributions/IntelliJ-EmmyLua2-{package_version}.zip'
target = workspace / 'upstream/Intellij-EmmyLua2/build/prepared'
target.mkdir(parents=True, exist_ok=True)
with tempfile.TemporaryDirectory(dir=target.parent) as temporary:
    extracted = Path(temporary) / 'IntelliJ-EmmyLua2'
    with zipfile.ZipFile(archive) as z:
        descriptors = []
        for name in z.namelist():
            if name.endswith('.jar'):
                with zipfile.ZipFile(io.BytesIO(z.read(name))) as jar:
                    if 'META-INF/plugin.xml' in jar.namelist():
                        descriptors.append(ET.fromstring(jar.read('META-INF/plugin.xml')))
        assert len(descriptors) == 1, 'Expected exactly one plugin descriptor'
        assert descriptors[0].findtext('version') == package_version
        unix_servers = [entry for entry in z.infolist() if entry.filename.endswith('/emmylua_ls')]
        assert {entry.filename.split('/')[-2] for entry in unix_servers} == {'darwin-arm64', 'darwin-x64', 'linux-x64'}
        for entry in unix_servers:
            mode = (entry.external_attr >> 16) & 0o777
            assert mode == 0o755, f'{entry.filename}: expected archive mode 0755, got {mode:04o}'
    # Python zipfile extraction does not restore Unix modes. Use the system extractor
    # and verify the result without repairing permissions, as an IDE installation would.
    subprocess.run(['/usr/bin/unzip', '-q', str(archive), '-d', temporary], check=True)
    server = extracted / 'server/darwin-arm64/emmylua_ls'
    assert server.stat().st_mode & 0o777 == 0o755
    assert subprocess.check_output([str(server), '--version'], text=True).strip().split()[-1] == version
    assert hashlib.sha256(server.read_bytes()).digest() == hashlib.sha256(binary.read_bytes()).digest()
    assert (server.parent / 'analyzer.lock').read_text() == (plugin / 'analyzer.lock').read_text()
    # Replace the generated verification copy so old versioned JARs cannot survive.
    destination = target / 'IntelliJ-EmmyLua2'
    if destination.exists():
        shutil.rmtree(destination)
    shutil.move(str(extracted), destination)
# Keep worktree builds beside earlier archives in the main repository.
shared_archive = workspace / 'upstream/Intellij-EmmyLua2/build/distributions' / archive.name
shared_archive.parent.mkdir(parents=True, exist_ok=True)
if archive.resolve() != shared_archive.resolve():
    shutil.copy2(archive, shared_archive)
assert hashlib.sha256(shared_archive.read_bytes()).digest() == hashlib.sha256(archive.read_bytes()).digest()
print('Verified development package:', shared_archive)
print('Verified development plugin:', destination)
PY
