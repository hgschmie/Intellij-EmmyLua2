# Local analyzer package

Run `bash scripts/build-module-patch.sh` from this repository on macOS arm64.
The expected layout is `<workspace>/upstream/Intellij-EmmyLua2` beside
`<workspace>/upstream/emmylua-analyzer-rust`, with shared build caches and the
LSP4IJ dependency beneath `<workspace>/spike`.

`analyzer.lock` pins the main-based analyzer development source at `34b9c107`.
The script checks its revision and tracked changes, builds the native server,
and produces `build/distributions/IntelliJ-EmmyLua2-0.25.1-115-IDEA262-patched-modules.zip`.
It verifies the plugin version, embedded pin, and native server hash, then
replaces the generated verification copy at
`<workspace>/spike/emmylua2-module-build/verified/IntelliJ-EmmyLua2`.

The reviewed workspace-resolution changes are included only in the macOS arm64
server. The other bundled platforms use upstream 0.25.1 release binaries.
Analyzer downloads are cached by version to avoid reusing older releases.

This builds a package; it does not install it into an IDE. The Factorio plugin
has a separate `emmy-analyzer.lock` pin that must match before its next build.
