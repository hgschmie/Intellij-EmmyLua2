# Cross-file diagnostic refresh development build

EmmyLua2 `0.25.1-116-IDEA262-patched-modules` packages analyzer development
commit `df78c6b02be03b7a0fd3cc6d0b44561c1aef3de3`.

The independent analyzer PR branch is `fix/cross-file-diagnostic-refresh`,
commit `69b1c31a`, based directly on upstream main. It does not depend on the
module-import patch. Both patches are merged into `work/intellij-modules`.

For clients supporting pull diagnostics and diagnostic refresh, Lua document
open/change and watched-file create/change/delete schedule a per-server refresh
using the diagnostic interval (500 ms by default). Rapid events coalesce. This
lets LSP4IJ refresh open consumers after a type annotation in another file changes.
The server also advertises inter-file diagnostic dependencies. Existing push-only
behavior is preserved; this patch does not solve cross-file refresh for those clients.

Validation: 215 server tests on the independent branch, 217 on the merged branch
(one existing ignored test in each). Protocol regression covers removal/restoration
of a required field, coalescing, external changes, deletion/recreation, and clients
without refresh support. Run from the analyzer repository:

```
python3 crates/emmylua_ls/tests/diagnostic_refresh.py target/release/emmylua_ls
```

Build with `bash scripts/build-module-patch.sh`. The ZIP is in
`build/distributions/`; its prepared dependency is in `build/prepared/`.
Install the ZIP in IntelliJ and restart. The plugin still includes the routing
hook, architecture selection fix, and legacy `global` member-name parser fix.
