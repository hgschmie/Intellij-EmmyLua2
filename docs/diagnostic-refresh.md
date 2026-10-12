# Cross-file diagnostic refresh development build

EmmyLua2 `0.30.0-135-IDEA262-patched-modules` packages the internal analyzer
revision in [analyzer.lock](../analyzer.lock). The standalone analyzer branch
`fix/cross-file-diagnostic-refresh` contains `8cbc71ca` and review follow-up
`1c47a240`, submitted as [PR #1283](https://github.com/EmmyLuaLs/emmylua-analyzer-rust/pull/1283).
It is based directly on upstream main and does not depend on other local patches.
Both commits are integrated into `work/intellij-modules`.

For clients supporting pull diagnostics and diagnostic refresh, Lua document
open/change, tracked removals on close, watched-file create/change/delete and
renames schedule a per-server refresh using the diagnostic interval (500 ms by
default). Rapid events coalesce. Configuration reload uses the same scheduler
without a debounce delay. This lets LSP4IJ refresh open consumers after a type
annotation in another file changes. The server also advertises inter-file
diagnostic dependencies.

Pending refreshes are cancelled before acknowledging shutdown and when closing
the server context. Outstanding update tasks cannot restart them. Timer tasks
hold a weak client reference; sending a refresh is serialized with cancellation
and replacement. Clients without refresh support receive no refresh requests.
Existing push-only behavior is preserved; this patch does not add cross-file
refresh for push-only clients.

Paused-clock Rust tests cover coalescing, debounce reset, zero delay, independent
clients and shutdown with retained snapshots. The protocol regression covers
required-field edits, close/deletion, watched events, rename, configuration
reload, capability gating and the shutdown-to-exit window. From the analyzer
worktree, run:

```sh
python3 crates/emmylua_ls/tests/diagnostic_refresh.py /path/to/emmylua_ls
```

The harness bounds process cleanup; it does not claim to fix the existing general
stdio-worker teardown behavior. See [build validation](../PATCHED_BUILD.md) for
combined analyzer and IntelliJ test results.

Build with `bash scripts/build-module-patch.sh` from the EmmyLua2 worktree. The
installable archive is copied to the main repository's `build/distributions/`,
and the prepared dependency to its `build/prepared/IntelliJ-EmmyLua2/`. Install
both the EmmyLua2 ZIP and FDE `0.7.2-dev`, then restart IntelliJ. The patched
analyzer is built for macOS arm64 only; other bundled servers remain upstream
0.25.1.
