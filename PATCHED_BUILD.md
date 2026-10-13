# Local patched build

Use `work/patched-build` in `$WORKSPACE/upstream/Intellij-EmmyLua2-worktrees/patched-build`, where
`WORKSPACE` is `~/ai/fmtk`. The current development package is
`0.30.0-136-IDEA262-patched-modules`. Its plugin ID is unchanged, so installing
this ZIP updates the existing EmmyLua2 plugin.

The work branch is based on upstream main `ee211107ae17169048d2d6b5f63040f19e355450`.
Merged contributions are supplied by that baseline; only server routing, the Lua
runtime selector and local packaging remain above it. Build 136 adds the named-cast analyzer fix from
[draft PR #1285](https://github.com/EmmyLuaLs/emmylua-analyzer-rust/pull/1285).

## Prerequisites and layout

- macOS arm64, Cargo/Rust, Python 3, and JDK 25.
- IntelliJ IDEA at `/Users/henning/Applications/IntelliJ IDEA.app` by default.
  Set `FMTK_IDEA_PATH` to use another local installation.
- JDK at `/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home` by
  default. Set `FMTK_JAVA_HOME` to use another JDK.
- Analyzer checkout at `$WORKSPACE/upstream/emmylua-analyzer-rust/build/checkouts/intellij-work`, on
  `work/intellij-modules`. Its HEAD must match [analyzer.lock](analyzer.lock),
  with no tracked changes. `FMTK_ANALYZER_PATH` can select a separate checkout
  of the same pinned revision, and `CARGO_TARGET_DIR` can select its build cache.
  The current pin is
  `8548f5d19da5c965f95886d6103b0cba6dbe5bd1`.
- Prepared LSP4IJ at `$WORKSPACE/dev/plugins/lsp4ij`. The current development
  dependency is `0.21.1-SNAPSHOT-factorio-formatting-patched2`, which includes
  upstream main and the file-specific server enablement fix. The earlier
  breakpoint-removal contribution is already part of upstream main. Prepare it with
  `bash "$WORKSPACE/intellij-factorio/scripts/build-lsp4ij.sh"`; see the Factorio plugin's
  [development layout](../../intellij-factorio/docs/DEVELOPMENT_LAYOUT.md).

Shared Cargo, Gradle and npm caches live in `$WORKSPACE/dev/cache/`.
The build home is `$WORKSPACE/dev/build-home`, and temporary files use
`$WORKSPACE/dev/tmp`. Logs and test reports belong in each repository's
`build/` directory. Rust compilation artifacts remain in the analyzer's
`target/` directory.

## Build and install

```sh
cd ~/ai/fmtk/upstream/Intellij-EmmyLua2-worktrees/patched-build
bash scripts/build-module-patch.sh
```

The script builds the pinned analyzer in release mode, packages the IntelliJ
plugin, checks the ZIP's version and bundled analyzer hash/source pin, and
refreshes the prepared plugin used by Factorio development builds.

The script keeps installable ZIPs together even when building from a worktree:

- Installable ZIP beside previous releases:
  `$WORKSPACE/upstream/Intellij-EmmyLua2/build/distributions/IntelliJ-EmmyLua2-0.30.0-136-IDEA262-patched-modules.zip`
- Identical worktree output: `build/distributions/IntelliJ-EmmyLua2-0.30.0-136-IDEA262-patched-modules.zip`
- Prepared dependency: `$WORKSPACE/upstream/Intellij-EmmyLua2/build/prepared/IntelliJ-EmmyLua2`
- Worktree build logs: `build/logs/build-module-patch-*.log`

Install the ZIP using **Settings → Plugins → Install Plugin from Disk**, then
restart IntelliJ. The prepared dependency is a generated build output, not a
second source checkout.

The custom analyzer fixes apply to the **macOS arm64 binary only**. Other
platform binaries remain upstream 0.25.1. The archive includes
`server/darwin-arm64/analyzer.lock` for provenance.

Use the packaging script for the development build: a direct `buildPlugin`
without `localAnalyzerPath` uses upstream analyzer binaries and the `-patched`
version suffix. The script fixes the build number at `136`; overriding
`RUNNER_NUMBER` in the calling environment does not change it.

## Upstream contributions and remaining local changes

Already included by upstream:

- [PR #59](https://github.com/EmmyLua/Intellij-EmmyLua2/pull/59): macOS architecture
  selection recognizes both JVM names `aarch64` and `arm64`.
- [PR #61](https://github.com/EmmyLua/Intellij-EmmyLua2/pull/61): legacy `global`
  member names parse before Lua 5.5; Lua 5.5 retains its reserved-word rules.
- [PR #62](https://github.com/EmmyLua/Intellij-EmmyLua2/pull/62): Lua indentation
  code-style settings.

Remaining IntelliJ contributions:

- Server routing: `fix/file-language-server-routing`, signed commit `b9f1ce9`,
  [PR #60](https://github.com/EmmyLua/Intellij-EmmyLua2/pull/60). It is the first
  commit above upstream on the work branch. The [extension contract](docs/server-routing.md)
  lets the Factorio plugin claim files for its per-module servers and route gutter
  requests. It also removes the duplicate Gradle import introduced by upstream's
  merge of the test setup.
- Default Lua runtime: `feat/default-lua-version`, signed commit `8186872`,
  [draft PR #63](https://github.com/EmmyLua/Intellij-EmmyLua2/pull/63);
  the equivalent work-branch commit is `1678db5`. This includes the settings-field
  width correction. See [language-level configuration](docs/language-level.md).

The pinned analyzer remains separate: module imports are already upstream through
PR #1266, while diagnostic refresh (PR #1283) and the other internal analyzer
patches are retained on `work/intellij-modules`. See [diagnostic refresh](docs/diagnostic-refresh.md)
and the analyzer's development handoff. Local build scripts, source pins and the
build-history documentation are work-branch changes, not upstream submissions.

Keep local package versions, source pins and build-layout changes off analyzer
contribution branches. The key-aware index follow-up below builds on the separate
local-table metatable association patch.

## Tests and validation

```sh
cd ~/ai/fmtk/upstream/Intellij-EmmyLua2-worktrees/patched-build
bash scripts/test-routing.sh
```

Despite its name, this script runs all plugin tests, including the parser tests.
Logs are in `build/logs/test-routing-*.log`; XML results are in
`build/test-results/test/`, and HTML reports in `build/reports/tests/test/`.
The local test init script selects IntelliJ Platform Gradle plugin 2.18.1 because
2.6.0 cannot parse the installed SDK's module descriptors for platform tests.
It limits loaded test plugins to EmmyLua and LSP4IJ to avoid an unrelated Ultimate
startup extension failure. These local accommodations are not part of the
contribution branches. This test command does not produce the pinned installable
ZIP; run `build-module-patch.sh` for that artifact.

Validation completed September 28, 2026:

- Eleven plugin tests passed: five routing and six member-name parser tests.
- The merged analyzer passed 217 server tests, with one existing ignored test.
- Diagnostic-refresh protocol tests passed for required-field removal/restoration,
  debouncing, external changes/deletion/recreation, and capability gating.
- All 12 module-import/type and FMTK locale protocol checks passed.
- Build 116 packaged successfully; its descriptor, arm64 binary hash and analyzer
  source pin were verified. Live IDE acceptance of its diagnostic-refresh fix
  remains separate from these automated checks.

Build 117 fixes Unix analyzer executable permissions in the ZIP. Packaging checks
all Unix analyzer entries for mode `0755`, extracts with the system unzip tool,
and runs the extracted arm64 server without applying a corrective chmod.

Build 118 includes the navigation-only analyzer fix for preceding top-level global
assignments in the same file (`fix/same-file-global-navigation`, `7935e3e0`),
merged into `work/intellij-modules` at `fed58df9`. Type inference is unchanged.

Build 119 adds **Editor → Code Style → Lua** for tab size, tabs versus spaces,
and editor indentation. The independent patch is `fix/lua-code-style-settings`
(`06881be`), merged into this work branch at `e36f86e`.

Build 120 includes the local-table metatable association fix
(`fix/local-table-metatable-association`, `f96347be`), merged into
`work/intellij-modules` at `bdacab57`. Stable local tables and aliases retain
function-valued `__index` behavior when exported. Reassigned bindings and member
targets remain excluded; precise inference of dynamic wrappers is unchanged.
The merged analyzer passed all 1,110 code-analysis tests.

Build 121 restricts native `global` member compatibility to explicitly supplied
Lua levels below 5.5. Unknown runtimes remain strict. The Factorio integration
supplies Lua 5.2 for its mod files via the independent language-level provider.
See [the extension contract](docs/language-level.md).

Build 122 adds the default Lua runtime selector under Languages & Frameworks →
EmmyLua Settings. Project runtime configuration overrides the default; Factorio
retains its managed Lua 5.2. Applying changes reparses Lua files and restarts only
standard EmmyLua servers. The feature is on `feat/default-lua-version`, based on
the version-aware parser patch. See `docs/language-level.md` for configuration
precedence and the native parser limitation for executable `.emmyrc.lua` files.

Build 123 restores the full-width analyzer path field in EmmyLua Settings while
keeping the Lua version selector compact.


Build 124 adds key-aware inference for simple expression-only `__index` functions.
The analyzer follow-up branch `fix/key-aware-index-metamethods` (`bf06cf50`) is
based on `fix/local-table-metatable-association`, and is merged into the work
branch at `592a6917`. For `return M[key] and function(...) ... end or nil`, with
`M` a closed local table, existing members resolve to functions and missing
members produce `undefined-field` diagnostics. Editing the provider updates
existing consumers. The wrapper's argument/return types still use ordinary
signature inference; this does not execute arbitrary function bodies.

Explicit return annotations, complex bodies, computed keys, reassigned captures,
and tables that escape into other code retain the broad inference fallback.
Validation: all 1,120 merged code-analysis tests passed, including ten new
regressions. A protocol test using the real `is.lua` and IntelliJ-style pull
refresh verified unsaved rename/restore and watched changes to a closed file,
without reopening the consumer or restarting the server.

Build 125 adds definition navigation for these key-aware `__index` lookups.
The separate analyzer follow-up `fix/index-metamethod-definition` (`eb8af4f2`)
builds on `fix/key-aware-index-metamethods` and is merged into
`work/intellij-modules` at `feca6e73`. `Is.Table` navigates to `M.Table`, and
`Is.Function` follows its alias to `M.Callable`. Direct backing-table lookups,
literal string/integer keys and simple forwarding wrappers are supported.
Ordinary definitions take precedence; rename/reference identities are unchanged.
Missing members, ambiguous branches, reassigned members and unsupported bodies
do not acquire synthetic navigation targets.

Validation: seven new definition tests pass, including provider edits and normal
member precedence. The merged analyzer passes 1,120 analysis tests and 226 server
tests, with one existing ignored test. A real LSP protocol test against a copy of
the mod's `is.lua` verifies exact destinations and diagnostics after unsaved
rename/restore and watched changes to a closed file. This is protocol validation;
live IntelliJ acceptance remains to be checked after installing the ZIP.

Build 126 adds hover documentation and semantic coloring for inferred members.
The analyzer branch `fix/index-metamethod-presentation` contains two signed fixes:
`f9e2f98c` adds backing-member documentation, and `438c9da9` colors inferred
function references like ordinary function members. Both are merged into
`work/intellij-modules` at `8647cfb1`.

Hover retains the actual inferred wrapper signature and labels documentation
borrowed from `M.Table` or `M.Callable`. Explicit wrapper/member/alias documentation
takes precedence. Existing LDoc comments remain text; EmmyLua parameter and
return annotations use the normal structured rendering. Function references such
as `local check = Is.Table` now receive the same method token as ordinary function
members; calls already received that token. Unknown and non-function values keep
their existing property styling.

Validation: eight new hover tests and three semantic-token tests pass. The merged
analyzer passes 1,120 analysis tests and 237 server tests, with one existing
ignored test. A protocol probe using a copy of the mod's `is.lua` checks hover,
definition targets and requested semantic tokens after unsaved documentation
edits, member rename/restore, and a watched edit to a closed provider. Live IDE
rendering and automatic token refresh are not asserted by these protocol checks.

## Asymmetric property types (build 127)

The analyzer includes `fix/asymmetric-field-types`: complementary `@field (read)`
and `@field (write)` annotations keep getter inference separate from setter
validation and assignment completion. Writes restore the getter type for later
reads. Property hover/completion documentation display both types and navigation
selects the matching declaration. Access restrictions and directional index
operators are not added.

The build scripts resolve shared `dev/` paths from the repository's common Git
directory, so they also work from `upstream/Intellij-EmmyLua2-worktrees/patched-build`.
The verified prepared dependency remains under the main repository's
`build/prepared/IntelliJ-EmmyLua2`.

## Nullable dictionary indexing (build 128)

The analyzer pin is `772ec76d` on `work/intellij-modules`, including independent
fix `3548b7f5` from `fix/nullable-table-index` and read/write compatibility tests.
Set `"strict": {"tableIndex": true}` in a module's `.luarc.json` or `.emmyrc.json`
to include `nil` in dictionary lookup results. This corrects the false
`unnecessary-if` warning for `if mods["nullius"] then`. It is disabled by default.
Required named fields, explicit `__index` function returns, iteration values,
and the separate read/write attribute behavior are preserved.

The combined analyzer passed 1,139 analysis, 239 LSP and 191 parser tests, with
one existing ignored LSP test. The fix also passed on upstream main without the
read/write patch. See the Factorio plugin's current acceptance notes for package
and protocol validation. Only the macOS arm64 server includes these local fixes.

Build 128 release packaging and all 22 plugin tests passed. The extracted release
analyzer passed the `mods["nullius"]` protocol check with `strict.tableIndex` both
disabled and enabled. Factorio `0.5.0-dev` passed 46 unit tests (one opt-in game
test skipped) and three relevant platform tests using the prepared build 128.
The main-repository and worktree archives have identical SHA-256 hashes; the
ZIP and extracted server retain executable mode `0755` and the expected source
pin. The build did not install the plugins into the regular IDE profile. On
2026-10-01, the user confirmed that the rebuilt patch works in their running
IntelliJ instance.

## Optional dictionary keys (build 129)

The analyzer pin is `e7ffd3b5` on `work/intellij-modules`. Independent branch
`fix/optional-dictionary-keys` contains signed commits `e3d5b79e` and `8dcd6c95`.
A `table<uint64, ProcInfo>` indexed by `uint64?` now produces `ProcInfo?`, removing
the false always-falsy condition warning. This works with `strict.tableIndex`
disabled or enabled. Literal and instantiated generic aliases are resolved,
including `Maybe<T> = T|nil`. Exact key-alias matches are reused during expansion
so large matching aliases avoid quadratic checking; nil branches are preserved.

Source validation passed 1,106 analysis, 213 LSP and 191 parser tests independently;
the combined work branch passed 1,157 analysis, 239 LSP and 191 parser tests. Each
LSP suite retains one ignored test. Coverage includes read/write compatibility,
named-field overrides, recursive generic aliases and 8,192-member key aliases.
The latter take about 6 ms in the debug regression test, versus seconds before
the review fixes. These timings are not release-server latency measurements.

Install this ZIP with Factorio `0.5.1-dev`. Only the macOS arm64 analyzer contains
the local patches. Release packaging and all 22 plugin tests passed. Archive
descriptors, analyzer pin, binary hashes and executable permissions (`0755`)
were verified. The extracted release server passed stdio LSP checks for the
original optional-key example and both alias cases with strict indexing disabled
and enabled. Factorio passed 46 unit tests (one opt-in game test skipped) and
three relevant platform tests against the prepared build 129. See its acceptance
notes for evidence paths. These builds have not been installed into the regular
IDE profile; manual acceptance remains pending.

## Function calls in assignment targets (build 130)

The analyzer pin is `a7ea9de6` on `work/intellij-modules`. Independent branch
`fix/duplicate-field-index-calls` contains signed commit `3ac513d3`, based directly
on upstream main `aaaca684`. The patch fixes an upstream false positive:
`inventory[tools.createItemIdentifier(item)] = item` previously reported that the
imported function was being redefined. The duplicate-field checker now requires
the member reference to be the complete assignment target. References used to
calculate the target's key or prefix are reads; direct writes still warn.

Two regression tests cover eight read-only cases and five actual redefinitions,
including bracket syntax, multiple assignment, function-valued right-hand sides
and function declarations. The read-only test fails before the fix. The independent
branch passed 1,093 analysis, 213 LSP and 191 parser tests; the merged work branch
passed 1,159 analysis, 239 LSP and 191 parser tests. Each LSP suite retains one
existing ignored test. All 22 EmmyLua2 plugin tests passed.

Install build 130 with Factorio `0.5.2-dev`. Only the macOS arm64 server contains
the local patches. Both release packages were verified for plugin versions,
analyzer pin, matching server hashes and executable permissions (`0755`). A
stdio LSP check against the packaged server confirms the original expression
has no duplicate warning while actual reassignment still warns. Factorio passed
46 unit tests (one opt-in game test skipped) and three relevant IntelliJ platform
tests. See its acceptance notes for evidence paths. Manual IDE acceptance remains
pending; these builds were not installed into the regular IDE profile.

## File-scoped formatting integration (build 131)

Rebuilt against the patched LSP4IJ dependency used by Factorio `0.5.3-dev`.
The analyzer source pin and binary behavior are unchanged from build 130.
LSP4IJ now respects each server's file-specific enablement when selecting a
formatter. Factorio also enforces module ownership at the formatting-feature
level, including when used with the earlier official LSP4IJ nightly.

All 22 EmmyLua2 plugin tests passed against the new LSP4IJ dependency. Release
packaging verified the plugin descriptor, analyzer pin and binary hash, `0755`
Unix server permissions, and matching worktree/main-repository ZIPs. Logs are in
`build/logs/file-scoped-131-{tests,build}.log`. The regular IDE was not updated.

## Reviewed formatting integration (build 132)

Rebuilt for Factorio `0.5.4-dev` against LSP4IJ
`0.21.1-SNAPSHOT-factorio-formatting-patched2`. The LSP4IJ PR #1688 follow-up
`4ef6aa5c` shares its matching predicate and rejects missing virtual files before
checking server enablement. No EmmyLua2 implementation or analyzer pin changed.

All 22 plugin tests passed. Packaging verified the plugin version, analyzer pin,
server binary hash and `0755` executable modes, then copied the ZIP to the usual
main-repository distribution directory and refreshed the prepared dependency.
Logs: `build/logs/file-scoped-review-132-{tests,build}.log`.

## Analyzer 0.30.0 integration (build 133, 2026-10-12)

The macOS arm64 server is rebuilt from `work/intellij-modules`, pin
`78c3ea62b1b3ace4e011310dd66fa1179ffc45fb`. That branch now starts at upstream
main `1c8589bb` plus the squashed and pushed PR #1266 commit `ef3cf3c8`.
All internal analyzer patches were ported to upstream's new semantic database;
independent contribution branches remain separate. See the analyzer's
`docs/intellij-development-handoff.md` for the port's scope and backup refs.

The analyzer passed all 2,455 workspace tests, rustfmt and workspace Clippy with
warnings denied. Real LSP checks passed module-local and cross-mod imports,
locale watcher updates, cross-file diagnostic refresh, and all 17 asymmetric
attributes in the installed Factorio 2.1.21 API. The package script checked the
ZIP version, analyzer source pin, binary hash, extraction and executable modes.

All 22 EmmyLua2 tests passed. FDE 0.7.0-dev passed 59 unit tests (one optional
native test skipped) and nine real-process IntelliJ platform tests, including
navigation/storage hovers in copies of the user's mods. Compatibility fixes on
the internal analyzer branch preserve API/event annotations, imported factory
signatures, class navigation and open-table hover fields. PR #1266 is unchanged.

The downloaded fallback binaries remain upstream 0.25.1; the package version
identifies the locally rebuilt 0.30.0 arm64 server. This is a macOS arm64
validation, not validation of the patched analyzer on other platforms.

Build log: `build/logs/analyzer-030-build133.log`. Plugin test log:
`build/logs/analyzer-030-plugin-tests.log`. No regular IDE installation is changed.

## Upstream module-resolution merge (build 134, 2026-10-12)

PR #1266 is now part of upstream main. The internal analyzer branch is rebased
onto `dbb68f470f1bd7f7bd7761dc661242e794340be8`, retaining upstream's additional
callable-table and tuple/dictionary fixes. The current analyzer pin is
`f6e67847612be9ae8822e7fad0e865849a78510c`. All remaining internal patches are retained; independent fix
branches are unchanged. The previous work tip is available as
`backup/internal-before-merged-module-rebase-2026-10-12`.

The only rebase conflict was in metatable indexing. Scanning all calls retains
both upstream's declaration/member initializers and the local standalone-call
support. The other 19 internal commits replayed without changes.

All 2,471 Rust workspace tests passed with all features, rustfmt and workspace
Clippy passed with warnings denied, and all 22 EmmyLua2 plugin tests passed.
FDE uses this package in version `0.7.1-dev`. Only the macOS arm64 server is
rebuilt; the fallback platform binaries remain upstream 0.25.1.

Evidence: `build/logs/merged-module-build134.log` and
`build/logs/merged-module-plugin-tests.log`; analyzer logs use the same
`merged-module-` prefix. See FDE's `docs/ACCEPTANCE.md` for integration results.

Final integration validation passed 59 FDE unit tests (one optional native-game
test skipped), all nine IntelliJ platform tests and the language-service,
module-service, diagnostic-refresh and asymmetric API protocol tests. The build
script verified the archive descriptor, source pin, binary hash and executable
permissions. Install both build 134 and FDE 0.7.1-dev; no regular IDE was updated.

## Diagnostic-refresh review integration (build 135, 2026-10-12)

The analyzer work branch merges PR #1283's review follow-up `1c47a240` through
`49a9ed3f`. The pinned revision is
`f5349db8ee7c970d654720fcb8b64b0736bc788b`, including the internal handoff update.
The merge adds exactly the reviewed ten-file follow-up; the other internal
patches and upstream `dbb68f47` baseline are unchanged. The contribution branch
remains separate and unchanged.

Refresh cancellation now happens before shutdown acknowledgement and on context
close, including when queued tasks retain snapshots. Timer tasks do not retain
the client. Tracked close/removal, rename and configuration reload use the same
capability-gated refresh scheduler. See [diagnostic refresh](docs/diagnostic-refresh.md).

All 2,473 analyzer workspace tests with all features, rustfmt and workspace
Clippy with warnings denied passed. All 22 EmmyLua2 tests passed on retry. The
initial run hit an existing LSP4IJ `LanguageServerWrapper.isActive()` null
`launcherFuture` race during a parser test; its report is preserved in
`build/reports/refresh-review-initial/`. No Java/Kotlin behavior was changed.

The protocol regression passed against the packaged arm64 binary, covering
edits, tracked close/deletion, watched events, rename, configuration reload,
capability gating and shutdown. ZIP checks verified the version, source pin,
binary hash and executable mode after extraction. Generated translations were
restored after Rust tests, before building the release server.

Install this build together with FDE `0.7.2-dev`; its integration validation is
recorded in FDE's `docs/ACCEPTANCE.md`. The patched server remains macOS arm64
only; other bundled servers remain upstream 0.25.1. LSP4IJ is unchanged.

Evidence: `build/logs/refresh-review-{build135,plugin-tests,plugin-tests-retry}.log`
and the analyzer worktree's `build/logs/refresh-review-{workspace,clippy,fmt,refresh-protocol,merge-check}.log`.
Archive SHA-256:
`927a0bae786ddfa0a23aca03c27df1ba7aaec4c455ab7cdd0203fa8bc31e6795`.

## Work-branch cleanup after upstream merges (2026-10-12)

Rebased `work/patched-build` onto upstream main `ee21110` and the current routing
commit `b9f1ce9`. The old tip `d0eb286` is retained at
`backup/patched-build-before-upstream-cleanup-20261012`. The code-style patch and
the two older global-member parser commits are no longer local patches. The
runtime selector and width correction are represented by the current combined
feature patch. All 29 work-only build/documentation commits were preserved.
Obsolete merge commits were flattened; other branches were not moved.

Before this documentation update, the only tree difference from the backup was
in `build.gradle.kts`: upstream's test plugin-loading setting and removal of one
blank line. Production sources, resources, analyzer pin and packaging versions
are identical. Existing build 135 / FDE 0.7.2-dev archives and the prepared
EmmyLua2 dependency were not rebuilt or replaced during this history cleanup.

Audit evidence: `build/logs/upstream-cleanup-{commits,refs-before,refs-after}.json`,
`build/logs/upstream-cleanup-range-diff.log` and
`build/logs/upstream-cleanup-todo.txt`.

All 31 commits above upstream have verified signatures. Gradle validation
(`scripts/test-routing.sh`) succeeded; `test` was up-to-date and reused the
existing result of 22 passing tests because sources and test inputs were
unchanged. No fresh test execution or package rebuild was needed. Evidence:
`build/logs/upstream-cleanup-plugin-tests.log` and
`build/logs/upstream-cleanup-signatures.log`.

## Build 136 — named cast expression scope (2026-10-12)

Pins analyzer `8548f5d19da5c965f95886d6103b0cba6dbe5bd1`, including signed
fix `b9a3d375` from [draft PR #1285](https://github.com/EmmyLuaLs/emmylua-analyzer-rust/pull/1285).
Named casts narrow their intended target without changing the enclosing array
lookup's result, and dynamic member keys retain their narrowed types. The
original reproduction now infers `ltn.FreeTrain?` and nonnullable `ltn.FreeTrain`
after `assert`.

The release build, archive version/pin/hash and executable-mode checks passed.
All 22 plugin tests passed. The packaged arm64 server passed the direct LSP cast
regression. FDE 0.7.3-dev passed 59 unit tests (one optional native-game test
skipped) and nine selected platform tests, one on retry after an IntelliJ
DiscoveryService teardown thread-leak assertion. Initial evidence is retained.
No regular IDE installation or interactive IDE/game acceptance was performed.

Evidence: `build/logs/named-cast-{build136,plugin-tests}.log`,
`build/reports/named-cast-plugin-tests/`, and FDE's `build/logs/named-cast-*`.
ZIP SHA-256: `46f8b212a1fe01c92fdd025a35f1ff47bfa3e36e44fd8a413024c048baebdb55`.
