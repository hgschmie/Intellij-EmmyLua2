# Local analyzer package

Run `bash scripts/build-module-patch.sh` from this repository on macOS arm64.
The expected layout is `<workspace>/upstream/Intellij-EmmyLua2` beside
`<workspace>/upstream/emmylua-analyzer-rust`, with shared build caches and the
LSP4IJ dependency beneath `<workspace>/dev` (`cache/` and `plugins/lsp4ij`).

`analyzer.lock` pins the main-based analyzer development source at `bdacab57`.
The script checks its revision and tracked changes, builds the native server,
and produces `build/distributions/IntelliJ-EmmyLua2-0.25.1-121-IDEA262-patched-modules.zip`.
It verifies the plugin version, embedded pin, and native server hash, then
replaces the generated verification copy at
`build/prepared/IntelliJ-EmmyLua2` in this repository.
Build and routing-test logs are written automatically to `build/logs/`.
The isolated build home and temporary files are in `<workspace>/dev/build-home`
and `<workspace>/dev/tmp`; analyzer outputs remain in its own `target/` directory.

The reviewed workspace-resolution changes are included only in the macOS arm64
server. The other bundled platforms use upstream 0.25.1 release binaries.
Analyzer downloads are cached by version to avoid reusing older releases.

This builds a package; it does not install it into an IDE. The Factorio plugin
has a separate `emmy-analyzer.lock` pin that must match before its next build.
