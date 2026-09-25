# Local patched build

Branch `work/patched-build` includes the macOS architecture fix from
`fix/macos-aarch64-server`: both JVM architecture names `aarch64` and `arm64`
select the bundled `darwin-arm64/emmylua_ls` server.

The default plugin version is `0.24.0-115-IDEA262-patched`. `RUNNER_NUMBER`
can override the build number. The plugin ID is unchanged, so installing the
ZIP updates the existing EmmyLua2 plugin.

To reproduce this build on the spike machine:

```sh
cd /Users/henning/ai/fmtk/Intellij-EmmyLua2
GRADLE_USER_HOME=/Users/henning/ai/fmtk/spike/cache/gradle \
JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home \
JAVA_TOOL_OPTIONS='-Duser.home=/Users/henning/ai/fmtk/spike/build-home -Djava.io.tmpdir=/Users/henning/ai/fmtk/spike/tmp' \
bash gradlew --no-daemon \
  -PlocalPlatformPath='/Users/henning/Applications/IntelliJ IDEA.app' \
  -PlocalLsp4ijPath=/Users/henning/ai/fmtk/spike/ide/plugins/lsp4ij \
  test buildPlugin
```

The optional local dependency properties used here select IntelliJ IDEA
2026.2.3 and LSP4IJ 0.21.0. Without these properties, Gradle downloads the
repository's configured IDEA SDK and LSP4IJ versions.

Install `build/distributions/IntelliJ-EmmyLua2-0.24.0-115-IDEA262-patched.zip`
using IntelliJ's **Install Plugin from Disk** action.

Validation: the full source build succeeded; the ZIP's plugin descriptor
contains the requested version. Running its compiled architecture selector
under IntelliJ's JBR 25.0.4 selected ARM for `aarch64` and `arm64`, and Intel
for `x86_64` and `amd64`. Gradle's test task reports `NO-SOURCE`; the repository
contains no automated test sources. This source-built ZIP has not yet been
installed and tested in the IDE.

## Multi-module development build

`bash scripts/build-module-patch.sh` builds the pinned Rust analyzer and packages
`0.24.0-115-IDEA262-patched-modules`. The additional server fix applies to the
**macOS arm64 binary only**; other platform binaries remain upstream 0.24.0.
The script requires the sibling workspace's `spike/emmy-analyzer-source` checkout
at `analyzer.lock`, branch `work/intellij-modules`. Cargo/Gradle caches and outputs
remain under the workspace. `-PlocalAnalyzerPath=...` is a development build input;
the archive includes `server/darwin-arm64/analyzer.lock` for provenance.

The upstream PR branch is `fix/workspace-relative-module-resolution` in that
Rust repository, based on upstream main. It contains the generic resolver fix,
regression tests and configuration documentation, without local build changes.
The 0.24.0 work branch contains its backport. The architecture fix remains in
this IntelliJ plugin's `work/patched-build` branch.

Validation on September 25: the 0.24.0 backport passes 1,082 analysis tests and
213 server tests (one existing ignored test); the current-main PR branch passes
1,096 analysis tests and 213 server tests. Clippy passes with warnings outside
the changed files. Multi-module and existing single-mod protocol checks pass.
In the isolated IDEA 262 profile, local completion/navigation selected each
mod's helper, and named cross-mod completion/navigation selected the other mod.
Completion UI was exercised with typed member prefixes; empty-prefix requests
returned correct server results but did not consistently appear in the client.
