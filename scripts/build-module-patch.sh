#!/bin/bash
set -euo pipefail
PLUGIN_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
WORKSPACE="$(dirname "$PLUGIN_ROOT")"
ANALYZER="$WORKSPACE/spike/emmy-analyzer-source"
if [ "$(uname -m)" != arm64 ]; then
  echo 'This development package builds the patched server for macOS arm64 only.' >&2
  exit 1
fi
if [ "$(git -C "$ANALYZER" rev-parse HEAD)" != "$(cat "$PLUGIN_ROOT/analyzer.lock")" ]; then
  echo 'Analyzer revision differs from analyzer.lock; review the pin before building.' >&2
  exit 1
fi
export CARGO_HOME="$WORKSPACE/spike/cache/cargo"
export CARGO_TARGET_DIR="$WORKSPACE/spike/emmy-target"
export TMPDIR="$WORKSPACE/spike/tmp"
cargo build --locked --release --manifest-path "$ANALYZER/Cargo.toml" -p emmylua_ls
export RUNNER_NUMBER=115
export GRADLE_USER_HOME="$WORKSPACE/spike/cache/gradle"
export JAVA_HOME="${FMTK_JAVA_HOME:-/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home}"
export JAVA_TOOL_OPTIONS="-Duser.home=$WORKSPACE/spike/build-home -Djava.io.tmpdir=$TMPDIR"
cd "$PLUGIN_ROOT"
bash gradlew --no-daemon --no-configuration-cache \
  "-PlocalPlatformPath=${FMTK_IDEA_PATH:-/Users/henning/Applications/IntelliJ IDEA.app}" \
  "-PlocalLsp4ijPath=$WORKSPACE/spike/ide/plugins/lsp4ij" \
  "-PlocalAnalyzerPath=$CARGO_TARGET_DIR/release/emmylua_ls" buildPlugin
python3 - "$PLUGIN_ROOT" "$WORKSPACE" <<'PY'
from pathlib import Path
import hashlib, sys, zipfile
plugin, workspace = map(Path, sys.argv[1:])
archive = plugin / 'build/distributions/IntelliJ-EmmyLua2-0.24.0-115-IDEA262-patched-modules.zip'
target = workspace / 'spike/emmylua2-module-build/verified'
with zipfile.ZipFile(archive) as z:
    z.extractall(target)
server = target / 'IntelliJ-EmmyLua2/server/darwin-arm64/emmylua_ls'
server.chmod(0o755)
assert hashlib.sha256(server.read_bytes()).digest() == hashlib.sha256((workspace / 'spike/emmy-target/release/emmylua_ls').read_bytes()).digest()
assert (server.parent / 'analyzer.lock').read_text() == (plugin / 'analyzer.lock').read_text()
print('Verified development plugin:', target / 'IntelliJ-EmmyLua2')
PY
