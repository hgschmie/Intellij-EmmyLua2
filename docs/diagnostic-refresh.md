# Cross-file workspace diagnostics development build

Build `0.30.0-152-IDEA262-patched-modules` replaces the old UP-008 refresh
workaround with UP-045 (`fix/workspace-diagnostic-scheduling`, `7306538d`).
The packaged analyzer revision is recorded in [analyzer.lock](../analyzer.lock).
Install it with LSP4IJ `0.21.1-SNAPSHOT-workspace-diagnostics-patched2` (UP-044).
Keep the existing FDE 0.7.8-dev installation.

Unsaved Lua edits, saves, watched changes/removals, renames and workspace reloads
now dirty the analyzer's workspace diagnostic generation. The client maintains a
workspace pull; the server completes it after a current analysis pass, then the
client requests the next one. Fast document pulls continue independently. Ordinary
edits do not send `workspace/diagnostic/refresh`; configuration reloads send it
only when the client advertises refresh support.

Reports carry actual open-document versions, clear removed files, and discard
passes overtaken by edits. Per-pass cancellation uses child tokens, so a retry
cannot cancel its parent request. Replacement requests and shutdown cancel idle
pulls promptly. Clients that use only document pulls do not receive UP-008's
former cross-file refresh workaround; they need workspace pulls for this path.

The protocol regression covers unsaved edits, watched edits/deletion/recreation,
rename, close, saves, configuration capability gating, request replacement and
shutdown. Run it from the analyzer worktree:

```sh
python3 crates/emmylua_ls/tests/workspace_diagnostics.py /path/to/emmylua_ls
```

The harness bounds process cleanup; it does not establish general stdio-process
teardown behavior. See [build validation](../PATCHED_BUILD.md) for combined
analyzer and IntelliJ test results. The regular IDE profile is not changed by
these builds or automated tests.

## Rollback

UP-008's branch `fix/cross-file-diagnostic-refresh` remains at `2cfe165c`.
The previous development analyzer is preserved at
`backup/intellij-modules-before-workspace-diagnostics-20261014` (`df28f68a`).
Its installable EmmyLua2 build151 ZIP is retained alongside build152. Reinstall
build151 to try the previous behavior; LSP4IJ patched2 can remain installed.
The old implementation and tests remain on those branches, not on the current
work tree. No replacement PR has been opened.

Build with `bash scripts/build-module-patch.sh` from the EmmyLua2 worktree. The
archive is copied to the main repository's `build/distributions/`, and the
prepared dependency to `build/prepared/IntelliJ-EmmyLua2/`. The custom analyzer
is built for macOS arm64 only; other bundled servers remain upstream versions.
