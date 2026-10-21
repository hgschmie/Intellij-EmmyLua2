# Build 186 — upstream refresh and member completion (2026-10-21)

Version/archive: `0.30.0-186` / `IntelliJ-EmmyLua2-0.30.0-186.zip`.
Analyzer pin: `36826d58c38a7134efb41b3a6f7dc8fbf72fdc13` on upstream/main `bb7c7d4c`.
Retains 65 contribution branches and internal UP-019/020/021. Upstream supplies
merged UP-046 and superseded UP-041/043/067/069/079/086; historical refs remain.
UP-099 fixes manual prefix completion aborting at a nonmatching candidate.
The real LTN `new_known_train.c` now offers `current_network_id` repeatedly.

All 3,018 combined tests (1,956 analysis / 315 server), 30 client routing tests,
Clippy and rustfmt pass. The real-mod comparison exposes eight diagnostics
with the same hover types as build185; tighter checking reveals existing flow/
iteration gaps and a potentially nil `first_signal` argument. The zero-warning
baseline is not preserved. Full diagnostic triage and delivery receipt:
workspace `upstream/memory/ANALYZER_REFRESH_2026-10-21.md`.

Only macOS arm64 receives the custom analyzer. Preserve FDE 0.9.2-dev and
LSP4IJ SNAPSHOT-13 archives. No push, new PR or installation into the running IDE.
Package descriptor/pin/binary identity/executable-mode validation passes; the ZIP
is copied flat into plugins/. SHA-256:
`522a19e5ea63992deae2a1e5b752237672df4808a304fd483db1ba59c312b5c3`.
Fourteen native FDE tests pass. Two broader integration timeouts also reproduce
with build185; two fixture-dependent tests lack their explicit path. The full
platform suite is not clean. Details and archived before/after evidence are in
the delivery report.

# Build 185 — collection-union iterator inference (2026-10-19)

Version/archive: `0.30.0-185` / `IntelliJ-EmmyLua2-0.30.0-185.zip`.
Analyzer pin: `4bf6a91ea91b031f44d99ca673bfd8d9c74983a4`. UP-097 independent signed `d2351942`
on upstream/main `51f2aa7b`, integrated by signed work pick `2a1e0199`.
Pairs inference follows inherited generic table bases and combines collection
union key/value types. Call-site signatures and loop variables agree; for the
actual LTN players union this is uint32|string|integer and LuaPlayer. The prior
UP-091/092 fixes remain, with no pending prerequisite for this patch.

All 2,548 standalone / 2,944 combined workspace tests, Clippy and rustfmt pass.
Seven packaged before/after real-API/actual-mod comparisons pass, and both
forced native IntelliJ tests pass (two executed, zero failures/errors/skips).
The archive's descriptor, pin, binary identity and executable modes pass; it is
copied flat into plugins/ and retained in both build/distributions folders.
SHA-256: `a03a5106c02f090056587a6471043721caca31ec4c3ececbc3f0090c3e7ee841`.

Only macOS arm64 receives the custom analyzer. FDE's development pin matches;
FDE 0.8.6-dev and LSP4IJ build10 ZIPs remain unchanged. No push, new PR or regular
IDE installation. Full report: workspace
`upstream/memory/LTN_UNION_ITERATOR_2026-10-19.md`.

# Build 184 — table constructor completion (2026-10-19)

Version/archive: `0.30.0-184` / `IntelliJ-EmmyLua2-0.30.0-184.zip`.
Analyzer pin: `17a76f41f43ac1d8c7fc2b3c13e120ef11f62500`.
UP-096 `fix/union-table-field-completion` / signed `89b80eb8` is independently
based on current upstream/main `51f2aa7b`. Work pick `a5929fe7` preserves the
existing stack without importing unrelated upstream changes. Table constructor
completion now suggests fields from table alternatives of unions/aliases,
excluding string-library methods and array-element fields. Normal string member
completion and field-value suggestions remain unchanged.

All 233 standalone and 313 combined server tests pass, including seven new
regressions. Build with `bash scripts/build-module-patch.sh`; it validates the
archive, embedded pin, binary identity and executable modes and refreshes the
prepared dependency. Copy the validated archive flat into workspace plugins/.
Only macOS arm64 receives this patched analyzer. Keep FDE 0.8.6-dev and LSP4IJ
SNAPSHOT-10 ZIPs unchanged; update FDE's development analyzer pin after validation.
No push, new PR or regular IDE installation. Final evidence and archive hash:
workspace `upstream/memory/LTN_TABLE_COMPLETION_2026-10-19.md`.

Archive validation and all 14 before/after completion checks passed, including
in-memory edits of the frozen actual LTN file. Both forced native IntelliJ
integration tests pass (two executed, zero failures/errors/skips). ZIP SHA-256:
`6a9707ec3cb460d191fa7d8291c808513e444ac7ef10d2d48d62eb42353a9d7c`.
The verified ZIP is copied flat into plugins/ and retained in both distribution
folders. FDE's development pin matches; existing FDE/LSP4IJ ZIPs are unchanged.

# Build 183 — equality and computed guard preservation (2026-10-18)

Version/archive: `0.30.0-183` / `IntelliJ-EmmyLua2-0.30.0-183.zip`.
Analyzer pin: `f1d218d402dcc38bcb43415234073093dd644d33`.
UP-095 preserves required values after inequality exit guards. The UP-078
amendment tracks stable local aliases and guards across nonconflicting branch
merges, retaining invalidation after relevant writes and visible mutators.
All previous patches remain; normal UP-045/UP-082 are retained.

The combined workspace passes all 2,929 tests (1,876 analysis / 306 server),
including the large linear-assignment stress test. Standalone analysis suites
pass 1,528 and 1,559 tests. Release raw-LSP replay of the same refactored LTN
snapshot removes both cache.lua diagnostics and the stop-update.lua392 warning.
The explicit-deletion negative control continues to warn.

Build with `bash scripts/build-module-patch.sh`; it validates the descriptor,
pin, analyzer identity and Unix0755 modes and refreshes the prepared dependency.
Copy the archive directly into workspace plugins/. Only macOS arm64 receives
the patched analyzer. Keep FDE 0.8.6-dev and LSP4IJ 0.21.1-SNAPSHOT-7 unchanged;
update only FDE's development pin after validating this archive. No push, PR or
IDE installation requested. Final checks and archive hash are recorded in
workspace `upstream/memory/LTN_EQUALITY_ALIAS_FIXES_2026-10-18.md`.

Archive validation passed; copied flat into plugins/. The packaged binary matches
the binary used for the successful LTN replay. SHA-256:
`fd511b115b55c88d01af359a399dd48f994d0a436eeb789bf6fcac14963438bc`.

# Build 182 — refreshed upstream and loop-cache integration (2026-10-18)

Version/archive: `0.30.0-182` / `IntelliJ-EmmyLua2-0.30.0-182.zip`.
Analyzer pin: `1c82cf231e824f279efec1f73bd32bd61415db16`; upstream/main base `3d4d1b73`.
The rebuilt work stack retains 68 pending contributions and work-only compatibility
changes. UP-050 and UP-056 are retired in favor of upstream d823745f/a0dcb60d.
UP-074 depends on UP-094's path-aware declaration memo: guard/cast facts remain
separate while equivalent routes share results. UP-074 and UP-082 use per-node
cycle metadata. Normal UP-045 remains active. All 2,921 combined tests pass.
The saved LTN replay retains exactly six diagnostics, zero in request_processor,
and concrete integer/FreeTrain iterator inference and hover.

Build with `bash scripts/build-module-patch.sh`. Archive validation checks the
version, embedded pin, binary identity and Unix0755 permissions. Copy the verified
archive flat into workspace plugins/. Only macOS arm64 has the patched analyzer.
Keep FDE 0.8.6-dev and LSP4IJ 0.21.1-SNAPSHOT-7; update the FDE development pin
after validation. Final branch and delivery evidence is recorded in
`upstream/memory/ANALYZER_REFRESH_2026-10-18.md`. No push, new PR or IDE installation.

Final archive SHA-256: `bc35a134108a9e791353efadccc9fba67ab17ff566464b286ebbc4417372d97e`.
Verified and copied flat into plugins/. All-target/all-feature workspace Clippy
and rustfmt pass; three forced native IntelliJ tests pass with no failures/errors/skips.
Packaged replay with the saved resource cache preserves the exact six-diagnostic
LTN baseline. The initial provisional archive was withheld and replaced after
UP-094 corrected seven new false warnings; its evidence stays under build/.

# Build 181 — refresh same-version analyzer resources (2026-10-18)

Version/archive: `0.30.0-181` / `IntelliJ-EmmyLua2-0.30.0-181.zip`.
Analyzer pin: `6a6d08d5bb443d4ad97b213c47241e36daba0bbf`.
UP-093 `fix/std-resource-cache-fingerprint` / signed ef3674de is independently
based on upstream/main dd80bd92, integrated as 80eedd82. Bundled-resource content
now participates in cache identity; explicit resource paths use their own marker.
Base extraction precedes translations and locale cache identities include
translation metadata. This refreshes UP-092's declarations in existing installs
whose analyzer package version is still 0.30.0. Same-content restarts reuse files.

The integrated branch passes three resource tests and all 306 server tests,
Clippy with all targets/features and rustfmt. Normal UP-045/UP-082 and all existing
fixes remain. Build with `bash scripts/build-module-patch.sh`; validate and copy
flat into workspace plugins/. Only macOS arm64 contains the custom analyzer.
Keep FDE 0.8.6-dev and LSP4IJ 0.21.1-SNAPSHOT-7; update only the FDE development
analyzer pin after package validation. No push, PR or IDE installation.
Receipt: `upstream/memory/LTN_RESOURCE_CACHE_UPGRADE_2026-10-18.md`.

Packaged upgrade replay using the copied live LTN cache now shows
integer/ltn.FreeTrain for pairs/ipairs; a second launch retains the corrected
hover and reuses cached files (unchanged mtimes). Full-mod diagnostics are
identical: six total, zero in request_processor.lua. Both forced native IntelliJ
tests pass with fresh 2-test XML and no failures/errors/skips.
Archive validation passed and the ZIP is copied flat into plugins/. SHA-256:
`df7d0ffd0153ed4f73ad7d61a465dae1c9abb968e31848c659ab4f3cae7296df`.
Standalone: three resource tests and all 228 server tests pass; Clippy/fmt pass.

# Build 180 — iterator inference and concrete hover (2026-10-18)

Version/archive: `0.30.0-180` / `IntelliJ-EmmyLua2-0.30.0-180.zip`.
Analyzer pin: `278036c34fe0b274ab54dd1ef0ad3025d9b2dd0b`.
Independent upstream/main dd80bd92 fixes UP-091 (`e41571d3`) and UP-092
(`df55badc`) restore ipairs array/dictionary loop types and concrete collection
call-site hovers. Library annotations describe the iterator/state/control triple.
Signed work picks ba4d368c/fd4e6a0d and amendment dee9b41e preserve existing
fixes including UP-041 ordinary-union matching. Normal UP-045/UP-082 remain.

All 2,908 combined tests pass across completed suites, with Clippy/rustfmt.
One formatter timing test exceeded its 10 ms limit under parallel execution;
all 304 server tests passed serially. No test was disabled or source changed
for that retry. Standalone UP-091 passes 1,524 analysis tests; UP-092 passes
1,525 analysis and 226 server tests.

Build with `bash scripts/build-module-patch.sh`; validation checks archive
version, embedded pin, binary identity and Unix0755 modes. Copy the validated
archive flat into workspace plugins/. Only macOS arm64 has the custom analyzer.
Keep FDE 0.8.6-dev and LSP4IJ 0.21.1-SNAPSHOT-7; update FDE's development
analyzer pin only after archive validation. No push, PR or IDE installation.
Receipt: `upstream/memory/LTN_ITERATOR_HOVER_INVESTIGATION_2026-10-18.md`.

Both forced native IntelliJ tests pass (asymmetric API attributes and separate
module workspaces), with fresh 2-test XML and no failures/errors/skips.

Packaged replay at actual LTN request_processor.lua583 now shows
integer/ltn.FreeTrain for both iterator signatures and both loop-variable pairs.
The fixed current-mod scan remains identical: six diagnostics, zero in
request_processor. Archive validation passed and the ZIP is copied flat into
plugins/. SHA-256:
`9ac2a206914c0742309b7b88869726dd1703905c18cf8d8e1d6a607b73b3ba25`.

# Build 179 — negative numeric literals (2026-10-17)

Version/archive: `0.30.0-179` / `IntelliJ-EmmyLua2-0.30.0-179.zip`.
Analyzer pin: `72e366218c4a1d6508d8ba97866413cebf1b5300`.
UP-090 `fix/negative-literal-hover` / signed `95d49887` fixes numeric unary-minus
inference and hovering the complete signed literal. Signed work pick `a0931520`
preserves UP-036 length inference, directional fields and metatable hover.
All 2,902 combined workspace tests, workspace Clippy and rustfmt pass.
Standalone: 1,522 analysis and 229 server tests pass, with Clippy/rustfmt.

Build with `bash scripts/build-module-patch.sh`; its archive validation checks
version, embedded pin, server identity and Unix0755 modes. Copy into the flat
workspace plugins/ folder. Only macOS arm64 receives the custom analyzer.
Keep FDE 0.8.6-dev and LSP4IJ 0.21.1-SNAPSHOT-7; update FDE's development pin
after package validation. Normal UP-045/UP-082 are retained.
No push, PR or IDE installation requested. Delivery evidence is recorded in
`upstream/memory/LTN_NEGATIVE_LITERAL_HOVER_2026-10-17.md`.

Packaged replay of the frozen real LTN init.lua143 now returns `-1` at both
sign and digit positions, with one range covering the whole expression. The
full frozen LTN scan is identical to build178 (104 diagnostics; request_processor
still zero). Both forced native IntelliJ integration tests pass, with fresh
2-test XML and no failures/errors/skips. ZIP SHA-256:
`0e587f82b57b42bbe7d84d9f5f68b4ac71547d9ced678474c2d7ea4d19b39576`.
The validated archive is copied flat into workspace plugins/.

# Build 178 — contextual callbacks and table-field hover (2026-10-17)

Version/archive: `0.30.0-178` / `IntelliJ-EmmyLua2-0.30.0-178.zip`.
Analyzer pin: `d69ba527115d65f2bd5ca2eabb56913223655c7a`.
UP-088 `fix/contextual-callback-parameters` / `4cedbc3a` supplies declared
callback parameter types from dictionary values and annotated local initializers.
UP-089 `fix/table-field-hover-context` / `aaae6fcf` stops table-field annotations
and constructor hover context leaking onto members inside callback bodies.
Independent on upstream/main d823745f; signed work picks c56755b4/dd91889d
preserve the work baseline. Integration d69ba527 checks strict/non-strict
callback hovers with and without individual entry annotations.

All 2,897 combined workspace tests, Clippy and rustfmt pass. Build with
`bash scripts/build-module-patch.sh`; it verifies the descriptor version,
embedded analyzer pin, binary identity and Unix0755 modes. Copy flat into
workspace plugins/. Only macOS arm64 receives the custom analyzer.
Keep FDE 0.8.6-dev and LSP4IJ 0.21.1-SNAPSHOT-7; update FDE's development
analyzer pin after validating this archive. Normal UP-045/UP-082 are retained.
Receipt: `upstream/memory/LTN_CONTEXTUAL_CALLBACKS_2026-10-17.md`.
Packaged LSP replay confirms all six minimal callback variants and every callback
parameter in the updated real settings.lua. Its message_level/member hovers retain
integer/boolean/number contracts. The frozen full LTN scan changes 79→104:
settings.lua gains newly visible assignment-contract warnings (34→58), and
stop-update.lua17 gains a valid always-truthy check on integer signal.count.
Both forced native IntelliJ API/separate-workspace and asymmetric-attribute tests
pass. ZIP SHA-256: `815661e90e75252cf1e1877d68c3ce74f46c71c95afe1af8115b9394b80b1586`.
Earlier outstanding work was pushed first; these new fixes/builds stay local.
No PR or IDE installation. Function-keyword hover remains language help.

# Build 177 — optional member hover presentation (2026-10-17)

Version/archive: `0.30.0-177` / `IntelliJ-EmmyLua2-0.30.0-177.zip`.
Analyzer pin: `c31e503d74deaf8e49eb40f0cdf570a348785ff5`.
UP-080 amendment `33e7abe3` is integrated as `a70ecda2`; UP-087 independent
`fix/optional-class-hover-fields` / `a8c897fe` is integrated through `82e42ad5`
and final work glue `aa7fb451`. Casts and guards narrow direct member hovers;
expanded object/class summaries retain optional field markers. The integration
preserves receiver-specific flow, generic optional fields, writes and method docs.

All 2,885 combined workspace tests, workspace Clippy and rustfmt pass. Build with
`bash scripts/build-module-patch.sh`; archive pin/version, server identity and
Unix0755 modes are validated. Keep all variants flat in workspace plugins/.
Only macOS arm64 includes the custom analyzer. Keep FDE 0.8.6-dev and LSP4IJ
0.21.1-SNAPSHOT-7; update FDE's development pin after the package is validated.
Normal UP-045/UP-082 remain. Receipt: `upstream/memory/LTN_OPTIONAL_HOVER_2026-10-17.md`.
No push, PR or user IDE installation.

# Build 176 — nullable class arguments (2026-10-17)

Version/archive: `0.30.0-176` / `IntelliJ-EmmyLua2-0.30.0-176.zip`.
Analyzer pin: `09a9f266920f2279193841aef7bf6955db8ac59e`.
UP-086 (`fix/nullable-required-arguments`, `76e288ed`, initial `38d309c6`) is independently based on
upstream/main d823745f and integrated as signed pick `31d29cf8`, avoiding the
unrelated upstream refactor. Nullable class values now warn when passed to
required parameters; optional contracts and guarded values remain accepted.
The use-site guard follow-up is signed pick `30e8aa46`; the exact real connector
call warns, while asserting its actual argument lookup removes the warning.
Work-only strict dictionary coverage is included. Normal UP-045/UP-082 remain.

Final combined Rust, Clippy, rustfmt and IntelliJ checks are recorded in the
validation receipt. Build with
`bash scripts/build-module-patch.sh`; it verifies the embedded version/pin,
server identity and Unix0755 modes. Copy the validated ZIP flat into plugins/.
Only macOS arm64 contains the custom analyzer. Keep FDE 0.8.6-dev and LSP4IJ
0.21.1-SNAPSHOT-7; update only FDE's development analyzer pin.
Final packaged/native verification and scope limits are recorded in workspace
`upstream/memory/LTN_NULLABLE_CONNECTOR_ARGUMENT_2026-10-17.md`.
Existing work/fix/comparison branches were pushed before this task. No new PR
or user IDE installation. General primitive/callback union behavior is unchanged.

# Build 175 — receiver documentation and explicit method display (2026-10-17)

Version/archive: `0.30.0-175` / `IntelliJ-EmmyLua2-0.30.0-175.zip`.
Analyzer pin: `9f1b230a529f61b3695dc45cb6bf71757e84f448`.
UP-084 (`fix/flow-receiver-hover`, `a6ec9d6a`) recovers member documentation
for receivers whose class becomes known through later assignments. UP-085
(`fix/explicit-method-hover`, `355a672f`) displays a colon only for an explicit
colon declaration or first parameter named self/_self. Both fixes are merged
into the work branch; UP-080, normal UP-045 and UP-082 are retained.

The combined workspace passes 2,861 Rust tests plus Clippy/rustfmt. Build with
`bash scripts/build-module-patch.sh`; copy the verified ZIP flat into plugins/.
Only macOS arm64 contains the custom analyzer. Keep FDE 0.8.6-dev and LSP4IJ
0.21.1-SNAPSHOT-7; update only FDE's development analyzer pin.
Final packaged replay/native validation and archive receipt: workspace
`upstream/memory/LTN_STOP_EVENTS_HOVER_FIXES_2026-10-17.md`.
No push, upstream PR or user IDE installation.

# Build 174 — nested receiver field contracts (2026-10-17)

Version/archive: `0.30.0-174` / `IntelliJ-EmmyLua2-0.30.0-174.zip`.
Analyzer pin: `d4f2b723b0bc8b58fe74391e93190b9de257a13d`.
UP-083 (`fix/nested-member-field-contracts`, `7daee751`) preserves declared
fields on narrowed nested receivers instead of using an inferred runtime write.
This fixes the LTN stop-update.lua601/608 integer-alias self-update warnings.
Normal UP-045 and the UP-082 performance fix are retained.

Build using `bash scripts/build-module-patch.sh`. It validates the embedded
pin/version, binary identity and Unix0755 permissions. Copy the validated ZIP
flat into workspace `plugins/`. Only macOS arm64 contains the custom analyzer.
Keep FDE 0.8.6-dev and LSP4IJ 0.21.1-SNAPSHOT-7; update only FDE's development
analyzer pin. Final standalone/combined/native/replay validation and package
receipt: workspace `upstream/memory/LTN_NESTED_FIELD_CONTRACTS_2026-10-17.md`.
No push, upstream PR or user IDE installation.

# Build 173 — member-flow performance (2026-10-16)

Archive/version: `IntelliJ-EmmyLua2-0.30.0-173.zip` / `0.30.0-173`.
Normal UP-045 scheduling is retained. UP-082 memoizes safe member-flow states;
the exact LTN annotation-edit replay drops from about 15 s to 340 ms without
changing diagnostics. Background workspace pulls can still cause contention.
All 2,846 combined workspace tests pass, plus Clippy, rustfmt and focused
branch/cast/acyclic-loop regressions. The defect predates our merged patches.

Analyzer pin: `ec216e69381ce505d08b6dc1707b031c2e8caabc`. Source: standalone
`fix/member-flow-trace-memoization` (`54aaa7b8`), integrated as `95e1c923`.
Build using `bash scripts/build-module-patch.sh`; archive validation checks the
embedded pin, binary identity and Unix 0755 modes. Only macOS arm64 carries the
custom server. A separate `173-UP-008` variant retains the comparison scheduler.
Keep FDE 0.8.6-dev and LSP4IJ 0.21.1-SNAPSHOT-7. See workspace
`upstream/memory/ANALYZER_EDIT_LATENCY_2026-10-16.md` for final measurements and receipts.

# Build 172 — shorter version and stop-update fixes (2026-10-16)

Current archive: `IntelliJ-EmmyLua2-0.30.0-172.zip`. Plugin version: `0.30.0-172`.
The build number is the local package identity; IDEA262/patched-modules suffixes
are removed. `RUNNER_NUMBER` is shared by Gradle and archive validation.

Analyzer pin: `3584adf440832a79863659e363f5d5f199ed3d08`.
Adds UP-079 optional parameter contracts, UP-080 narrowed member hover and
nullable receiver method documentation, and UP-081 pairs/ipairs entry facts.
All earlier local fixes remain. Source branches and regression provenance are in
workspace `upstream/memory/LTN_STOP_UPDATE_2026-10-16.md`.

Build with `CARGO_TARGET_DIR=../../emmylua-analyzer-rust/target` (or an absolute
workspace-local path) and `bash scripts/build-module-patch.sh`; its default target
also works. The script checks the committed analyzer pin and validates the ZIP,
plugin descriptor, binary identity and Unix 0755 modes before preparing the shared
dependency. The shared archive remains in `upstream/Intellij-EmmyLua2/build/distributions/`;
a validated copy is also in workspace-root `plugins/`.

Validation: 2,844 analyzer workspace tests, Clippy/rustfmt, 30 Emmy routing/client
tests, and real LSP replay of stop-update.lua/request_processor.lua. The initial
sandboxed IntelliJ test JVM could not register with macOS AppKit; the permitted
rerun outside the sandbox passed. Explicit outputs remain in the workspace.
Only macOS arm64 contains this custom analyzer. FDE keeps 0.8.6-dev and its ZIP.

# Refreshed upstream work stack — build171 (normal UP-045)

Version: `0.30.0-171-IDEA262-patched-modules`.
Analyzer pin: `ea1f7fbb2134f50522f4178ade5dcc0732d8725e`.

The analyzer work branch is reconstructed on upstream/main `ef6c06a7` with all
54 retained contributions and the work-only API/integration changes. Obsolete
UP-009 and already-upstream UP-077 are omitted; UP-008 remains inactive.
Full validation passes 2,832 tests (1,823 analysis / 267 server), Clippy with
warnings denied and rustfmt. The frozen LTN replay improves 82 → 81 diagnostics,
with no new diagnostic locations. request_processor.lua and the newer tools.lua
snapshot remain clear. EmmyLua2's 30 client/routing tests pass.

Only macOS arm64 contains the custom analyzer. Build with
`scripts/build-module-patch.sh`; it verifies the descriptor, analyzer pin, binary
identity and Unix 0755 permissions. Copy the validated archive directly into
workspace `plugins/`. FDE keeps 0.8.6-dev and its existing ZIP. LSP4IJ is maintained
separately and is not rebuilt or modified by this task. No IDE installation,
push or PR operation. Final artifact/integration receipt:
workspace `upstream/memory/ANALYZER_WORK_REBUILD_2026-10-16.md`.

FDE compatibility: 64 unit tests pass (one existing skip), plus all eight
native IntelliJ checks after correcting the test's asynchronous-restart wait.
The final packaged server matches the release replay. Archive SHA256:
`73124b9fe3be4eb737c085fb1848951f326707092fa06edb31fc883d414bfc4b`.

Older build entries below are historical.

# LTN tools.lua guard effects — build170 (normal UP-045)

Version: `0.30.0-170-IDEA262-patched-modules`.
Analyzer pin: `0f6001bb5bdb7c64abd7e918e1dff1d676805df9`.

Includes UP-077 (upstream first-result truthiness backport) and UP-078
(computed guard/assignment effect boundaries). Opaque calls preserve guards as
requested; visible conflicting mutations still invalidate them. Child writes
and nonconflicting nested loops preserve the parent container's guard.

Standalone 1,547 analysis tests and combined 1,770 analysis/267 server tests pass,
with Clippy and rustfmt. Release raw-LSP comparison against the current LTN
script/tools.lua removes all three warnings, leaving zero diagnostics there.
The full union/variadic upstream rewrite is deliberately not imported.

Only EmmyLua2 needs installation. Keep FDE 0.8.6-dev, LSP4IJ patched4 and normal
UP-045. FDE's development analyzer pin changes without another FDE ZIP.
Build with scripts/build-module-patch.sh; descriptor, pin, binary identity and
0755 checks are built in. Copy the validated ZIP flat into workspace plugins/.
Only macOS arm64 contains the patched analyzer. No push, PR or IDE installation.
Final integration/package evidence: workspace upstream/memory/LTN_TOOLS_GUARDS_2026-10-16.md.
Packaged current tools.lua has zero diagnostics; the frozen full-mod baseline
falls86→82 (72→68 warnings, unchanged14 hints), with no new diagnostics.
FDE64 unit tests and two native API tests pass (one existing unit skip).
Archive SHA256: `3bc8c043cd24f575cce4996e2bfdfdcdee6c9785736f48c8a6f1bdbb3875d7b1`.

# LTN dictionary keys and assignment contracts — build169 (normal UP-045)

Version: `0.30.0-169-IDEA262-patched-modules`.
Analyzer pin: `d6a763941c80d17046b4356d7e7caa243ba48073`.

Includes independent UP-075 (string aliases as dictionary/class index keys) and
UP-076 (mutable inferred literal assignment contracts). Explicit annotations,
inline assertions, named casts and literal read precision remain intact.
UP-075 shares alias expansion with UP-042 in the combined work branch.
Each standalone fix passes 1,521 analysis tests, Clippy and rustfmt on upstream
ca9bec3b. Combined validation passes 1,764 analysis and 267 server tests, Clippy
and rustfmt. Packaged LTN diagnostics drop from 89 to86 (75 to72 warnings,
unchanged14 hints), with no new diagnostics and request_processor.lua still clear. The larger new upstream union refactor is not included in this package.
See workspace upstream/memory/LTN_PRIORITY_FOLLOWUPS_2026-10-16.md for combined tests,
packaged LTN results, prioritization and the pre-existing class-key limitation.

Only EmmyLua2 needs installation. Keep FDE 0.8.6-dev, LSP4IJ patched4 and normal
UP-045. FDE's development pin is updated without producing another FDE ZIP.
Build with scripts/build-module-patch.sh; it checks descriptor, analyzer pin,
binary identity and Unix 0755 modes. Only macOS arm64 has the custom analyzer.
Copy the ZIP flat into workspace plugins/. No push, PR or IDE installation.

# LTN guard and loop fixes — build168 (normal UP-045)

Version: `0.30.0-168-IDEA262-patched-modules`.
Analyzer pin: `19f594561ee476f888b044269f0f976df67f6dd0`.

Includes UP-071–074: stable nested/global dictionary initialization, computed
class-field guards, optional-field conditions and loop-exit assignments. Work
integration preserves inline casts with UP-061 and optional-field guards in large
functions. Actual cycle paths remain conservatively uncached; acyclic loop
summaries avoid repeated declaration traces.

All 1,758 combined analysis tests and 267 server tests pass with all features,
as do Clippy and rustfmt. Standalone fixes have independent regression coverage.
See workspace upstream/memory/LTN_FLOW_TRIAGE_2026-10-15.md for full evidence and the
remaining LTN diagnostics. Install with FDE 0.8.6-dev for the separate Problems
information/hint counts and filters. Keep LSP4IJ patched4; UP-008 comparison159
is unchanged. Only macOS arm64 receives the custom analyzer.

Build using scripts/build-module-patch.sh. Its archive checks verify the pin,
descriptor, binary identity and Unix 0755 modes. Copy the validated ZIP flat into
workspace plugins/. Previous outstanding heads were pushed first; these new
fixes remain local. No PR or IDE installation.

# LocalisedString arrays — build167 (normal UP-045)

Version: `0.30.0-167-IDEA262-patched-modules`.
Analyzer pin: `624585085b10e6ae2c7bad7d443edf3c14b5e873`.

UP-069 (`fix/array-union-alias-compatibility`, signed a916fa61; work pick
bcb37d34) accepts arrays through named/recursive union alternatives in casts,
returns and arguments. Element checks and recursion guards remain in force.
All 1,477 standalone /1,747 combined analysis and 267 server tests pass, including
slow-feature coverage, as do Clippy and rustfmt.
UP-041's named-union argument handling is complementary, not a prerequisite.

Install with FDE 0.8.5-dev, which bundles UP-070's documented LocalisedString
runtime/data aliases, including runtime nil. The original #1195 false warning
no longer reproduces; invalid-field controls remain rejected. The native FDE
regression fails as expected with build166 and the corrected aliases.

Build with scripts/build-module-patch.sh; validation checks Unix 0755 modes and
checks the pin, descriptor and binary identity. Only macOS arm64 receives our
custom analyzer. Keep LSP4IJ patched4 and the UP-008 comparison159 unchanged.
Copy the validated ZIP flat into workspace plugins/. Full test/package evidence:
workspace upstream/memory/LOCALISED_STRING_FIXES_2026-10-15.md.
Prior outstanding commits were pushed first; new changes remain local. No PR or
IDE installation.

# Named variadic compatibility — build166 (normal UP-045)

Version: `0.30.0-166-IDEA262-patched-modules`.
Analyzer pin: `6f99b56090ffef93a6a3c5464a38c3d32efbe24a`.

UP-067 (`fix/variadic-named-type-compatibility`, signed 4982e0ee; work pick
792564e1) routes genuine variadic enum/class/alias elements through ordinary
compatibility checks. Incompatible types remain rejected. All 1,739 combined
analysis and 267 server tests, Clippy and rustfmt pass.

Remote calls also need UP-068's corrected FMTK annotation: ReturnType<FF> already
represents the complete return sequence in EmmyLua, so it must not be wrapped in
another ellipsis. InterfaceMap lookup itself works. FDE 0.8.4-dev bundles that
separate toolkit fix. Install both packages; keep LSP4IJ patched4. The older
UP-008 comparison159 is unchanged. Only macOS arm64 receives the patched analyzer.

Build with scripts/build-module-patch.sh. Archive pin, binary identity, descriptor
and executable-mode validation remain enabled; copy the validated ZIP flat into
workspace plugins/. Full evidence is in workspace
upstream/memory/VARIADIC_NAMED_TYPES_2026-10-15.md. No push, PR or IDE installation.

# init.lua and remote interface fixes — build165 (normal UP-045)

Version: `0.30.0-165-IDEA262-patched-modules`.
Analyzer pin: `312a96f93c7161326855e2fb33c55dc3aca748eb`.

Includes UP-064 (declared member self-assignment inference), UP-065 (unknown
logical-and guards), and UP-066 (dependent generic remote-call constraints).
The UP-066 checker follow-up and an UP-019 integration adjustment preserve
generated API generic argument checks and return signatures.
Each is an independent signed fix branch on upstream/main 96545f45, integrated
through signed cherry-picks while preserving the local stack. UP-066 predates
our merged contributions: it fails at the initial upstream analyzer rewrite.

Build using `scripts/build-module-patch.sh`; its native build, pin, archive and
0755 verification remain unchanged. The local patched server is macOS arm64 only.
The validated ZIP is retained in both build/distributions locations and copied
flat into workspace plugins/. Retain the existing FDE 0.8.3-dev and LSP4IJ patched4
archives; update only FDE's development analyzer pin. UP-008 comparison remains159.
Validation: 1,731 combined analyzer tests, 267 server tests, Clippy/rustfmt and
both native FDE API integration tests pass. Packaged raw-LSP checks clear all
20 init.lua diagnostics and preserve the other checked LTN baselines. Full
validation/provenance is in workspace upstream/memory/INIT_REMOTE_FIXES_2026-10-15.md.
No new PR, push, original-mod edit or IDE installation for this build.

# Declared field hover — build164 (normal UP-045)

Version: `0.30.0-164-IDEA262-patched-modules`.
Analyzer pin: `a03b57592474a34596e92b0956e6e5b99188429a`.

UP-063 (`fix/declared-field-hover`, signed `aa1a7abc`) retains the declared field
contract on assignment-target hovers while still displaying the assigned literal.
LTN `storage.tick_state` now shows `ltn.TickState = 0` instead of `number = 0`.
The receiver is resolved in scope, including globals and nested generic fields.
Explicit runtime annotations, function signatures and untyped fields retain
existing behavior. Core inference and diagnostics are unchanged. No pending
prerequisite; UP-062's numeric enum compatibility fix is complementary.
Signed work pick: `3eed5f00`.

Build with `bash scripts/build-module-patch.sh`. Copy the validated archive from
the shared distributions folder directly to plugins/. Only macOS arm64 receives
the patched analyzer. Keep FDE 0.8.3-dev and LSP4IJ patched4; FDE's development
pin advances without a new ZIP. UP-008 comparison remains build159.
No push, PR or IDE installation. Final evidence:
workspace `upstream/memory/DECLARED_FIELD_HOVER_2026-10-15.md`.

# Numeric enum literal compatibility — build163 (normal UP-045)

Version: `0.30.0-163-IDEA262-patched-modules`.
Analyzer pin: `d213c89b6127f5a9a497920b94f1b48b525eeb0c`.

UP-062 (`fix/enum-literal-compatibility`, signed `8b7d14af`) fixes false
mismatches for numeric enum members in returns, arguments and assignments.
Membership uses the table initializer in the defining file, including aliases
and optional enum targets. Every source alternative must satisfy the enum
contract; a nil alternative cannot hide an invalid literal.

This independent upstream/main fix complements UP-028's cross-file lookup,
UP-031's class-valued enum members and UP-058's explicit enum casts. Signed
work pick `e5b987cf` preserves all existing fixes. Only macOS arm64 receives
the patched analyzer. Build with `bash scripts/build-module-patch.sh` and
copy the validated ZIP from the shared distributions folder directly to plugins/.
Keep FDE 0.8.3-dev and LSP4IJ patched4; FDE's development analyzer pin advances
without producing a new FDE archive. The UP-008 comparison remains build159.
No push, PR or IDE installation. Final validation and provenance:
workspace `upstream/memory/ENUM_LITERAL_COMPATIBILITY_2026-10-15.md`.

# Guarded collections and loop-carried values — build162 (normal UP-045)

Version: `0.30.0-162-IDEA262-patched-modules`.
Analyzer pin: `37ab7b5b78083973a589d465183f46abcfd56b90`.

- UP-060 (`fix/stable-global-index-guards`, signed `d5b17179`) extends shared
  condition facts to global roots, literal/field keys and inherited dictionary
  contracts. Guards remain invalidated by mutations, calls and scope changes;
  explicit `__index` handlers remain conservative. Prerequisites: UP-033/059.
- UP-061 (`fix/loop-carried-literal-types`, signed `54ea951c`) accounts for
  assignments from later loop iterations instead of retaining the initial literal
  value. Fresh locals, independent resets and annotated parameter/local contracts
  keep their appropriate precision. This fix is independent on upstream/main.

Both minimal bugs reproduce in the initial upstream refactor, before our first
merged contribution. Signed work picks preserve all existing fixes. Build with
`bash scripts/build-module-patch.sh`; copy the verified archive from the shared
`upstream/Intellij-EmmyLua2/build/distributions` folder directly to `plugins/`.
Only macOS arm64 receives the patched analyzer. Install this EmmyLua2 ZIP and
restart IntelliJ; keep FDE 0.8.3-dev and LSP4IJ patched4. FDE's development pin
advances without a new FDE ZIP. UP-008 comparison remains build159.
No IDE installation, push or PR for the new fixes. Final evidence/report:
workspace `upstream/memory/CONSOLE_GUARDS_LOOP_2026-10-15.md`.

# Enum assertions and inherited dictionaries — build161 (normal UP-045)

Version: `0.30.0-161-IDEA262-patched-modules`.
Analyzer pin: `d3dde8476c43c7e415d36f878030dfaa2f0eee1f`.

- UP-058 (`fix/enum-cast-compatibility`, signed `183158c5`) restores primitive
  casts to compatible enum values, including aliases and cross-file declarations.
  This is a type assertion, without runtime validation or conversion.
- UP-059 (`fix/inherited-dictionary-index`, signed `3704f8cd`) retains generic
  key/value types from dictionary supertypes such as LuaCustomTable. Optional keys
  produce nullable values. Explicit members and invalid-key diagnostics remain.

Both standalone fixes are based independently on upstream/main `96545f45`.
Their signed work cherry-picks preserve all earlier fixes. Separate work glue
connects inherited contracts to UP-016/017's strict dictionary read/write policy.
Both bugs reproduce in the initial upstream refactor, before our first merged PR.

Build with `bash scripts/build-module-patch.sh`. Copy the validated ZIP from
`upstream/Intellij-EmmyLua2/build/distributions` directly into workspace `plugins/`.
Install that EmmyLua2 ZIP and restart IntelliJ. Keep FDE 0.8.3-dev and LSP4IJ
patched4; only macOS arm64 contains the custom analyzer. The UP-008 comparison
stays at build159. FDE's development pin advances, but its existing ZIP stays.
No installation, push or PR performed. Final validation report:
workspace `upstream/memory/ENUM_CAST_CUSTOM_INDEX_2026-10-15.md`.

# Class member identity — build160 (normal UP-045)

Version: `0.30.0-160-IDEA262-patched-modules`.
Analyzer pin: `cb881b4d10b9d3f71850b22e0c4c5d32b4f6fd40`.
UP-057 (`fix/class-member-value-identity`, signed `d28acce3`) fixes false
assignment mismatches for `M.__index = M` and other class-valued members. The
declaration query preserves class/enum identity instead of expanding the runtime
initializer into an anonymous table. Explicit types and invalid-assignment
diagnostics remain intact. No prerequisite local patch; all existing work fixes
remain included through signed cherry-pick `d0097b01`.

Build with `bash scripts/build-module-patch.sh`. Copy the validated archive from
the shared `upstream/Intellij-EmmyLua2/build/distributions` folder directly into
workspace `plugins/`. Install the EmmyLua2 ZIP and restart IntelliJ; keep FDE
0.8.3-dev and LSP4IJ patched4. Only macOS arm64 contains the patched analyzer.
FDE's development analyzer pin is updated, but its existing ZIP is unchanged.
The one-off UP-008 comparison remains at build159. No push, upstream PR or
installation for this fix. Final test/protocol/package report:
workspace `upstream/memory/CLASS_MEMBER_IDENTITY_2026-10-15.md`.

# Project diagnostics and require resolution — build159 (normal UP-045)

Analyzer pin: `ce7e11e911c8788f7ce88e3db0af3573e45ceb33`. Includes UP-056: existing Lua modules no longer
produce unresolved-require warnings simply because they have no return value or
an unknown export. All previous fixes are retained. This is the normal UP-045 variant;
UP-045 remains the normal work baseline. Both variants are rebuilt because the
user is currently comparing UP-008 in IntelliJ.

Install with FDE 0.8.1-dev, which fixes all-module service startup and retention.
LSP4IJ workspace-diagnostics-patched4 is unchanged. Only macOS arm64 contains the
patched analyzer. The script builds and validates the ZIP in the usual shared
`upstream/Intellij-EmmyLua2/build/distributions` folder, preserving older archives
and keeping normal/comparison prepared dependencies separate. Clear a custom
server override to use the bundled server. No push or PR. Final evidence:
workspace `upstream/memory/PROJECT_SERVICES_2026-10-15.md`.

---

# Current development package — build158 (UP-045)

Version: `0.30.0-158-IDEA262-patched-modules`. Analyzer pin: `3c9cdfb4ce4cd42f710f49f41115c8f3053e06e2`.
UP-054 (`fix/request-update-ordering`, signed `c0f71524`) makes requests wait for
preceding queued document updates before reading analysis. Semantic highlighting
and completion can no longer overtake those edits. Waiting remains cancellable;
the message loop and update worker stay independent of request execution.
Standalone base: upstream/main `96545f45`; work cherry-pick `0d95174e` preserves
UP-045's shutdown handling. All previous fixes, including UP-053, remain included.

The user chose UP-045. The one-off UP-008 branch/package stays at build157 and is
not advanced. Only macOS arm64 includes the patched analyzer. Keep FDE 0.7.9-dev
and LSP4IJ workspace-diagnostics-patched3; FDE's existing ZIP is unchanged. Install
this EmmyLua2 ZIP and restart IntelliJ with the custom server override empty.

Build with `bash scripts/build-module-patch.sh`; the ZIP is copied to the common
`upstream/Intellij-EmmyLua2/build/distributions` folder and the normal prepared
dependency is refreshed. No push or PR was requested. Final tests, hashes and
remaining latency limits: workspace `upstream/memory/REQUEST_ORDERING_2026-10-14.md`.

---

# Previous annotation-completion package — build157

Version: `0.30.0-157-IDEA262-patched-modules`. Analyzer pin: `f44bc15020e7c9bfc0c0b73dfadc49550146fc1e`.
Contains UP-053 (`fix/doc-type-completion`, signed `7e080f55`), independently based
on upstream/main `96545f45`: visible API/library/metadata types are offered in
annotations; qualified prefixes and mid-token edits complete correctly. Scope
visibility is preserved. Both packages retain all previous analyzer fixes,
including UP-051/UP-052. Only macOS arm64 contains the patched analyzer.

This variant keeps UP-045 (normal development baseline).
The missing-suggestion repair is identical in both packages. It does not repair
the separate diagnostic contention or edit/request ordering issues. Install one
EmmyLua2 variant at a time and restart IntelliJ; leave custom server location empty
to use the bundled analyzer. FDE remains 0.7.9-dev, LSP4IJ stays
workspace-diagnostics-patched3. FDE's archive is unchanged.

Build: `bash scripts/build-module-patch.sh`. ZIPs are copied into the common
`upstream/Intellij-EmmyLua2/build/distributions` directory. Comparison builds use
`build/prepared-up008`, never the normal prepared dependency. No push or PR.
Final evidence: workspace `upstream/memory/DOC_TYPE_COMPLETION_2026-10-14.md`.

---

# Previous development package — build156

The normal package is `0.30.0-156-IDEA262-patched-modules`. It retains UP-045 and
adds UP-051 (callable class overloads) and UP-052 (constructor call self/overload
alignment), with the existing inline assertion and union checks preserved.
Only macOS arm64 receives the patched analyzer. Keep FDE 0.7.9-dev and LSP4IJ
workspace-diagnostics-patched3. FDE's ZIP is unchanged.

A separate `experiment/up008-completion-comparison` branch builds the one-off
`0.30.0-156-IDEA262-patched-modules-up008` package with UP-008 replacing UP-045.
The normal prepared plugin and development pins must remain on this branch.
Both variants include the same entities fixes and unchanged completion code.

Analyzer pin: `54b5a910887ae4f880d5c1e8b5b17df0e3e2d796`. Signed standalone fix commits: `5fbac291`,
`0307732f` on upstream/main `96545f45`. Nothing pushed and no PR opened.
Final evidence/hashes: workspace `upstream/memory/COMPLETION_COMPARISON_2026-10-14.md`.

---

# Local patched build

Use `work/patched-build` in `$WORKSPACE/upstream/Intellij-EmmyLua2-worktrees/patched-build`, where
`WORKSPACE` is `~/ai/fmtk`. The current development package is
`0.30.0-155-IDEA262-patched-modules`. Its plugin ID is unchanged, so installing
this ZIP updates the existing EmmyLua2 plugin.

The work branch is based on upstream main `a3a78d965d55a8d24ddfff41f839c0432c314cd0`.
Merged contributions, including the default Lua runtime selector, are supplied by
that baseline; server routing, workspace ownership and local packaging remain above it. Build 148
fixes named union aliases in parameter checks, including both valid custom-event
calls at LTN request_processor.lua:811 and :910. Existing
module, API, formatter and analyzer fixes remain included. LSP4IJ
`0.21.1-SNAPSHOT-workspace-diagnostics-patched3` supplies workspace-pull support. The current analyzer contribution status is recorded below;
no new upstream PRs are opened for this build.

## Build 155 bare-table inference and namespace inheritance (2026-10-14)

Analyzer pin `fb7a3502ae2c69aba522e4caed5860a40a23603e` includes two independent
fix branches against upstream/main `96545f45`:

- UP-049: `fix/bare-table-member-inference` (`c4694854`, `46d93e89`). Bare-table
  members stay unrestricted, including after field guards and generic copies.
  Structured generic object arguments retain their known field types.
- UP-050: `fix/namespaced-constructor-inheritance` (`5dec3623`). Constructor field
  existence and assigned-value checks resolve inherited members in the declaring
  namespace, preserving required/optional/unknown-field validation.

Signed cherry-picks `e205e991`, `3eabc2c5` and `e5b1656a` preserve the existing work
baseline and pending dictionary/read-write patches. Both contribution branches
remain local; no new PR or push. Standalone analysis suites pass 1,474 and 1,475
tests respectively. All 2,645 combined workspace/all-feature Rust tests pass,
as do Clippy with all targets/features and -D warnings, and rustfmt.

Install build155 with existing FDE 0.7.9-dev and LSP4IJ workspace-diagnostics-patched3.
FDE's development analyzer pin is updated; its ZIP is unchanged. Only macOS arm64
receives the locally patched analyzer. Packaged protocol/platform evidence and
final hashes are in workspace `upstream/memory/DATA_UTIL_FIXES_2026-10-14.md`.

The packaged-server check removes data-util.lua's false condition warnings at
lines 26/29/32 and the inherited Sprite field errors. The namespace-only fixture
has zero diagnostics; the four remaining warnings in the original file are its
separate Sprite/coordinate annotation mismatches. Frozen LTN request_processor.lua
and interface.lua remain warning-free. The original mod was not changed.
Archive SHA-256: `4323e3590c66a0d8cf8e958df93382ca5abaf44468cbc7d3670e5d1168f11706`.
Analyzer SHA-256: `ef8b6b6a9bb57494bf8bc6717703e186f5777726aea63bebc4a72da0bc1c5c4b`.
Pins, binary identity and Unix 0755 modes are verified.

## Build 154 workspace ownership (2026-10-14)

UP-047 adds an optional owned-root API to the existing file-routing provider.
The default analyzer omits owned workspace roots and receives directory exclusions
for owned folders nested under a broader root. Provider changes stop the default
server; unclaimed editors restart it on demand with fresh ownership. FDE 0.7.9-dev
publishes its reserved modules and managed library roots. Explicit analyzer
workspace/library/package configuration stays under user control.

Contribution branch `feat/workspace-root-ownership` (`b4573b5`) is based on the
pending routing contribution `b9f1ce9` (UP-006/#60). Its signed work copies are
`23ce780`, `61283d6` and `c786770`. No new PR or push.

Analyzer pin `bc5c9b5a3a1261e0acc17fb70ed93e626cd75a04` includes UP-048,
`fix/mixed-config-key-merging` (`a9684a5c` on upstream/main `96545f45`). Normalizing
configuration layers before merging preserves native dotted exclusions alongside
the nested ownership overlay. The bug reproduced with both unit regressions and
the real default-server test. All 1,472 standalone and 1,640 combined analysis
tests pass, as do 30 EmmyLua2 tests. FDE platform/package evidence is recorded in
workspace `upstream/memory/WORKSPACE_OWNERSHIP_2026-10-14.md`.

Install build154 with FDE 0.7.9-dev; keep LSP4IJ workspace-diagnostics-patched3.
Only macOS arm64 receives the locally patched analyzer, as in previous builds.
Archive SHA-256: `c09eaf6017bdc025dd6b6585b764e85c88eeb4f47687bcc81fd17ced3fe574da`.
Analyzer SHA-256: `c516b027623dbb7544029df0e581dda4cb7bf949d8a1b8767f7031e8001a8a13`.

## Build 153 multi-root duplicate classes (2026-10-14)

Analyzer pin `0f4d0c10ca8eefc010caf1aa48d6b5129bfcda8a` includes UP-046, `fix/unique-workspace-id-aggregation`
(`dc511eeb`, current upstream/main base). The MAIN index is aggregated once even
when multiple physical roots share it. Removing a file preserves genuine class
definitions from other files. Other patches, including UP-045, remain included.

Install with LSP4IJ workspace-diagnostics-patched3: its independent DocumentMatcher
fix prevents default EmmyLua workspace reports from leaking into FDE mod files.
FDE stays 0.7.8-dev; no new FDE archive is needed. Source commits are local and
not pushed. Final evidence is in workspace upstream/memory/DUPLICATE_METRICS_2026-10-14.md.

Build153 validation: the packaged server no longer reports duplicate-type for
LTN metrics.lua with the generic server's eight workspace folders; its unrelated
line44 assignment warning remains. The frozen LTN request_processor.lua and
interface.lua baseline has zero diagnostics, and all four FDE platform checks
pass with patched3 (API workspaces, asymmetric attributes, Lua version and unsaved
cross-file diagnostics). Archive SHA-256:
`adc814702317d5be211bdf8d2f3ac58bcaff9ed4c857d3226780df61be35f690`.
The analyzer SHA-256 is `5009e2d7a4807494131e8777e067e5e928ff6b29e94c1116287bcbf9b37d2dee`;
packaged/prepared binary identity, source pin and Unix 0755 are verified.

## Build 152 workspace diagnostic scheduling (2026-10-14)

Analyzer pin `a8307e66561e661834e9783a6e7fc2fc4538b5de` replaces UP-008 in the active work tree with UP-045,
`fix/workspace-diagnostic-scheduling` (`7306538d`, based on upstream/main `96545f45`).
The fix is cherry-picked with provenance onto the existing work baseline to avoid
importing the unrelated upstream HashMap refactor. UP-008's original branch and
`backup/intellij-modules-before-workspace-diagnostics-20261014` are retained.

Install this EmmyLua2 ZIP together with LSP4IJ workspace-diagnostics-patched2.
Ordinary edits now update dependent-file diagnostics through workspace pulls;
they no longer trigger the old per-edit global refresh request. Closed files,
open-file document priority, file removals, reloads and cancellation are covered
by the server regressions. See workspace `upstream/FIX_INVENTORY.md` and HANDOFF.md
for final integration results. FDE remains 0.7.8-dev; its archive is unchanged.

Validation: 226 standalone server tests and 2,627 combined workspace/all-feature
Rust tests pass, with Clippy (-Dwarnings) and rustfmt. Seven protocol scenarios
pass on both standalone and packaged servers; the unsaved-edit case times out
on unpatched upstream. Four FDE platform tests pass, including automatic warning
appearance/clearing in open and closed consumers after unsaved type-file edits.
Frozen LTN request_processor.lua and interface.lua still have zero diagnostics.
No interactive UI test or regular-profile installation was performed.

ZIP SHA-256: `d658f723b640dbd152d3c16eb9d09dc9c33ec112b32c875de2375a0769da09f2`.
Analyzer SHA-256: `efbef8886cf539c3d13b06bf026e7ba9bd6539d2ea124c0055e61b690303fea4`.
Pins, packaged/prepared binary identity and Unix 0755 permissions are verified.
Evidence: analyzer `build/investigations/workspace-diagnostic-scheduling/verification.json`,
work `build/logs/workspace-scheduling-*.log`, and FDE
`build/logs/workspace-diagnostic-platform.log`. Source commits remain local;
no push or PR was requested for UP-045.

## Build 151 upstream refresh (2026-10-14)

Analyzer pin `df28f68aed39d36041ee7d5f43da9c361f015f41` uses upstream/main 7096fef5.
#1285 (named casts), #1289 (generic display) and #1291 (dictionary constructors)
are supplied by upstream. The remaining 27 fix branches are rebased. #1283 was
closed without merging and remains a local refresh patch; #1286/#1292 stay open.
#1290 was closed as superseded by #1288. Production source and tests are identical
to build150; this refresh changes ancestry and package provenance.

Validation and final artifact evidence are recorded in workspace
upstream/memory/ANALYZER_REFRESH_2026-10-14.md. FDE remains 0.7.8-dev. The frozen LTN
request_processor.lua snapshot remains the zero-diagnostic acceptance baseline.

## Build 150 upstream refresh (2026-10-13)

Analyzer pin `5ff43630d2dcf704e5b1c4e4204e317f81acf80c` reconstructs work on upstream/main a0ce1512.
The obsolete inline-assertion implementation is replaced by upstream #1288;
its twelve regression tests remain. Current fix branches and dependency stacks
are recorded in the workspace's upstream/FIX_INVENTORY.md. Both reported LTN
warnings (dynamic string aliases and integer return types) are fixed.

Final combined validation passed all 2,629 workspace/all-feature tests, Clippy
with warnings denied, and formatting. Every retained contribution branch passed
its standalone suite and checks. Debug, release and packaged build-150 servers
report zero diagnostics in the frozen request_processor.lua and
interface.lua using the existing module configuration. No new suppressions.
FDE remains 0.7.8-dev. Packaging and platform evidence are recorded in the
workspace's upstream/memory/ANALYZER_REFRESH_2026-10-13.md.

## Build 149 baseline (2026-10-13)

UP-042 (`7541a401`) fixes named dynamic-string member keys and UP-043
(`5dbe626d`) fixes integer alias returns into number slots. Both reproduce on
upstream. Each standalone branch passes 2,415 workspace/all-feature tests,
Clippy and formatting. Initial work pin `1bdab7f2` integrates both, with a
small adapter for the older union API. The combined suite passes 2,602 tests.
Packaged-server replay now reports zero diagnostics in the fixed LTN snapshot's
request_processor.lua and interface.lua, using the module's existing
unnecessary-assert setting. Three FDE platform checks pass. FDE buildPlugin is
up to date; its version remains 0.7.8-dev. This established the baseline before the completed build-150 refresh above.
Evidence: analyzer `build/investigations/analyzer-refresh-20261013/`.

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
  `a8307e66561e661834e9783a6e7fc2fc4538b5de`.
- Prepared LSP4IJ at `$WORKSPACE/dev/plugins/lsp4ij`. The current development
  dependency is `0.21.1-SNAPSHOT-workspace-diagnostics-patched2`, adding workspace
  pulls to the upstream base containing our merged server-selection and
  breakpoint-removal contributions. Prepare the pinned development archive with
  `bash "$WORKSPACE/intellij-factorio/scripts/build-lsp4ij.sh"`; see the Factorio plugin's
  [development layout](../../../intellij-factorio/docs/DEVELOPMENT_LAYOUT.md).

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
  `$WORKSPACE/upstream/Intellij-EmmyLua2/build/distributions/IntelliJ-EmmyLua2-0.30.0-152-IDEA262-patched-modules.zip`
- Identical worktree output: `build/distributions/IntelliJ-EmmyLua2-0.30.0-152-IDEA262-patched-modules.zip`
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
version suffix. The script fixes the build number at `152`; overriding
`RUNNER_NUMBER` in the calling environment does not change it.

## Upstream contributions and remaining local changes

Already included by upstream:

- [PR #59](https://github.com/EmmyLua/Intellij-EmmyLua2/pull/59): macOS architecture
  selection recognizes both JVM names `aarch64` and `arm64`.
- [PR #61](https://github.com/EmmyLua/Intellij-EmmyLua2/pull/61): legacy `global`
  member names parse before Lua 5.5; Lua 5.5 retains its reserved-word rules.
- [PR #62](https://github.com/EmmyLua/Intellij-EmmyLua2/pull/62): Lua indentation
  code-style settings.
- [PR #63](https://github.com/EmmyLua/Intellij-EmmyLua2/pull/63): default Lua runtime
  selector and settings-field width correction. See [language-level configuration](docs/language-level.md).

Remaining IntelliJ contributions:

- Server routing: `fix/file-language-server-routing`, signed contribution commit `b9f1ce9`,
  [PR #60](https://github.com/EmmyLua/Intellij-EmmyLua2/pull/60). It is the first
  commit above upstream on the work branch. The [extension contract](docs/server-routing.md)
  lets the Factorio plugin claim files for its per-module servers and route gutter
  requests. The work-branch copy is rebased on the new upstream baseline; the
  duplicate Gradle import correction is now supplied by upstream PR #63.

The pinned analyzer remains separate: module imports are already upstream through
PR #1266. Build 152 replaces the diagnostic-refresh workaround (UP-008/PR #1283)
with workspace-diagnostic scheduling (UP-045). UP-008 and the previous work head
are preserved for rollback; the other internal analyzer patches remain included. See [diagnostic refresh](docs/diagnostic-refresh.md)
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

## Build 137 — named cast review follow-up (2026-10-12)

Pins analyzer `4b1a0c2cef5f358bef03ba356038286cded26f64`, including signed
review follow-up `3b0d6831` on [draft PR #1285](https://github.com/EmmyLuaLs/emmylua-analyzer-rust/pull/1285).
The VM uses an enum for static/dynamic index operands and debug stack checks;
regressions additionally cover owner/statement casts, nil/never key types and
nested/recursive computed keys. Signed merge `697f751d` integrates the exact
follow-up into the internal analyzer branch.

All 2,392 standalone and 2,478 combined analyzer workspace tests passed with all
features, along with Clippy (warnings denied) and rustfmt. All 22 EmmyLua2 plugin
tests passed. The packaged arm64 server passes the original cast/index/assert
LSP reproduction; ZIP version, pin, binary hash and executable modes match the
release and prepared binaries. FDE 0.7.4-dev passed all nine platform tests on
the first run; integration results are recorded in its `docs/ACCEPTANCE.md`.
No ordinary IDE installation or interactive test.

Evidence: `build/logs/cast-review-{build137,plugin-tests}.log`,
`build/reports/cast-review-plugin-tests/`, FDE `build/logs/cast-review-*`, and
analyzer `build/investigations/cast-index/packaged137/`.
ZIP SHA-256: `2366a953236e1fc28fc0671ce52dd11f25a23fab282b011182ac407057900ea8`.


## Build 138 — flow-aware union member arguments (2026-10-12)

Pins analyzer `f653fa59ba576db2c2330b41e14b2702ddde62b9`. The argument diagnostic
check uses the owner's flow type after casts, guards and assignments, avoiding
synthetic nil mismatches from discarded union members. [PR #1286](https://github.com/EmmyLuaLs/emmylua-analyzer-rust/pull/1286)
contains `430fe1de` and review follow-up `272d3096`; signed merges `b62f2f41` and
`3862d97d` integrate them. Its eleven regressions cover narrowed/widened unions,
nested/recursive owners, branch isolation and actual incompatible field types.

The combined tests exposed a PR #1285 compatibility gap for ambiguous union
members without a unique member ID. Follow-up `4dbecfe5`, merged as `525a6f0f`,
restores the immediate named cast only for the exact static member path and
resolved root declaration. Unique-member casts keep their existing tracing;
unrelated owners and enclosing expressions stay unaffected. The pre-existing
lack of ambiguous-cast persistence after an intervening statement remains;
this correction does not add a general flow walker. Both PRs are ready for review.

All 2,492 combined Rust workspace tests passed with all features, alongside
workspace/all-target Clippy with warnings denied and rustfmt. The PR #1285
follow-up separately passed all 2,395 standalone tests. All 22 EmmyLua2 tests,
59 FDE unit tests (one optional native-game skip) and all nine selected IntelliJ
platform tests passed. The platform tests required no retries.

The packaged server was tested against a copy of all 37 LogisticTrainNetwork Lua
files. The reported argument diagnostics on request_processor.lua lines 365/366
are gone; its complete diagnostic count fell from 69 to 58, with eleven nil
mismatches removed and no added diagnostics. The original cast/index probe still
returns `ltn.FreeTrain?` for the raw lookup and `ltn.FreeTrain` after `assert`.
Archive descriptor/version, source pin, Unix executable permissions and matching
release/prepared/ZIP server bytes were verified. Only the macOS arm64 server
carries the custom patches; other bundled servers remain upstream 0.25.1.
No ordinary IDE installation, interactive IDE/game test or Plugin Verifier run.

Evidence: `build/logs/union-argument-{build138,plugin-tests}.log`,
`build/reports/union-argument-plugin-tests/`, FDE `build/logs/union-argument-*`
and `build/reports/union-argument-{unit,platform}/`, analyzer worktree
`build/logs/union-argument-*`, and main analyzer repository
`build/investigations/union-member-argument/real-mod-packaged138/` and
`build/investigations/cast-index/packaged138/`.

ZIP SHA-256: `b53caf8025cdae10f84067fccffc83172fbffb1de751c146c0d07b2849cb35ae`.
Arm64 server SHA-256: `21e0c03fca8997fa2a195e16599296283d27a02374e2d5aba8ccc5750269c6dc`.

## Build 139 — table rendering and inline assertions (2026-10-13)

The analyzer pin is `8b32942e911f4ed49351f8383b41d09aa140d557` on
`work/intellij-modules`. Two independent upstream fixes address the
LogisticTrainNetwork table annotation reports:

- [PR #1289](https://github.com/EmmyLuaLs/emmylua-analyzer-rust/pull/1289)
  preserves explicit generic arguments in rendering. Built-in `table` declarations
  have no declared generic parameters; the renderer previously dropped their
  supplied key/value types and displayed `table<>`.
- [PR #1290](https://github.com/EmmyLuaLs/emmylua-analyzer-rust/pull/1290)
  confines inline `@as` assertions to their attached expression. A constructor's
  `@as ltn.FreeTrain` no longer casts its individual fields or unrelated later reads.

Both branches start at upstream main `cb05b0c0`, were submitted as drafts, and
received review follow-ups before integration. Signed backports `71c30d1c`,
`6c341ed2`, `f5ee2ad1`, and `18ea33d9` preserve our existing Arc baseline without
importing unrelated upstream changes. Work-only commit `581ba186` applies the
same expression-local rule to our directional-field read walker and adds a
compatibility regression. Both PRs have 15 successful CI checks and the expected
skipped release job; no additional feedback appeared at the final check.

All 2,510 combined Rust workspace tests passed with all features, plus
workspace/all-target Clippy with warnings denied and rustfmt. All 22 EmmyLua2
tests, 59 FDE unit tests (one optional native-game skip), and nine selected
IntelliJ platform tests passed. No platform retry was needed. Platform coverage
includes real Lua/FMTK processes, per-module routing/formatting, lifecycle/EDT,
dependency edits, configuration reload, generated API/asymmetric fields, copied
real-mod navigation/storage and Lua language level.

The packaged server was tested with copies of all 37 LogisticTrainNetwork Lua
files. Hover now displays `table<integer,ltn.FreeTrain>` and the constructor
fields retain LuaTrain, LuaSurface and integer types. File diagnostics decreased
from 57 to 53: four false constructor-field warnings removed, with no new warning
locations. An existing LocalisedString/boolean warning at line 889 now names the
literal string instead of the enclosing assertion type. The earlier line 365/366
warnings remain absent, and every cast/index probe case matches packaged build
138, including FreeTrain? lookup and FreeTrain after assert. The standalone
minimal constructor probe also passes with and without the inline assertion.

Archive IDs/versions, analyzer pins, hashes and Unix 0755 executable modes were
verified. The release, prepared and ZIP arm64 server bytes match. Only macOS arm64
carries the custom analyzer patches; other bundled servers remain upstream
0.25.1. LSP4IJ/toolkit pins are unchanged. No ordinary IDE installation,
interactive IDE/game test or Plugin Verifier run was performed. Original mods
were not changed. Internal branches remain local; only PR branches were pushed.

Evidence: this checkout's `build/logs/table-annotations-*` and
`build/reports/table-annotations-plugin-tests/`; FDE
`build/logs/table-annotations-*` and `build/reports/table-annotations-{unit,platform}/`;
analyzer work `build/logs/table-annotations-*`; main analyzer
`build/investigations/table-annotations/{packaged139,real-mod-packaged139}/`,
`build/investigations/cast-index/packaged139/` and
`build/investigations/union-member-argument/real-mod-packaged139/`.

Artifacts:

- `upstream/Intellij-EmmyLua2/build/distributions/IntelliJ-EmmyLua2-0.30.0-139-IDEA262-patched-modules.zip`
- `intellij-factorio/build/distributions/intellij-factorio-0.7.6-dev.zip`

ZIP SHA-256:

- EmmyLua2: `d1021bdd5068aab43bd5349cf8138b8c1a7f1e6e115dbd99787ffa14aadd13bf`.
- FDE: `cebe773a4f01dee4faba385a085e587972bdb3a3d79fcaa9df72016805d0d63c`.

Arm64 analyzer SHA-256: `d9a1a4a2f0c118bd6e3e50ac21d24e20aa1f3f0568c570f1e54d1fd132fed867`.

## Build 140 — dictionary constructors and enum tables (2026-10-13)

Analyzer pin `5a064469ebcbfdd7faca3bfcbd24ee4ba716ebb8` retains the previous
work branch and adds two independent diagnostic fixes based on upstream main
`cb05b0c0`. Both failures originate in the upstream semantic rewrite, not our
local patches. Neither branch was pushed and no PR was created, at the user's
request; future PR descriptions are saved in each checkout's ignored
`build/PR_DESCRIPTION.md`.

- `fix/dictionary-constructor-fields`, signed `cdf32d10`: the field checker
  treated `table<K,V>` as the standard library table class's closed record
  surface. A narrow guard matches the existing assignment checker's treatment
  of dictionaries. Class missing/extra field checks remain enabled. This does
  not add dictionary key/value validation.
- `fix/cross-file-enum-members`, signed `7727a58f`: the checker looked for an enum
  runtime declaration in the current file and checked its enum type rather than
  its constructor's fields. It now uses the owning file and runtime table surface.
  Enum-typed values and missing fields remain errors, including in conditions.

Signed backports `5c39ca41` and `14ac6b9e` applied cleanly to the existing work
branch; no compatibility glue or unrelated upstream changes were needed.
Standalone checker/type-check suites passed 639 and 638 tests, respectively,
with Clippy and formatting checks. The new positive regressions fail before
fixing. All 2,515 combined workspace tests with all features, workspace/all-target
Clippy with warnings denied, and rustfmt passed.

All 22 EmmyLua2 tests, 59 FDE unit tests (one optional native-game test skipped),
and nine selected IntelliJ platform tests passed on the first run. Platform tests
exercise real Lua/FMTK processes, routing/formatting, lifecycle/EDT, dependency
and configuration updates, generated APIs/asymmetric fields, copied real-mod
navigation/storage and Lua language level.

The packaged server was tested against copies of all 37 LogisticTrainNetwork
Lua files. Comparing build 139 with 140 gives constants.lua 17→0 diagnostics,
request_processor.lua 53→46, and cache.lua 21→8. No diagnostics were added. All
reported dictionary constructor and DISTANCE_RESULT member warnings disappeared.
Original mod files and the ordinary IDE profile were not changed.

Archive IDs/versions, pins, hashes and Unix 0755 executable permissions were
verified. Release, prepared and archived arm64 server binaries are identical.
Custom analyzer changes remain macOS arm64 only; other bundled servers remain
upstream 0.25.1. LSP4IJ/toolkit pins are unchanged. No interactive IDE/game test
or Plugin Verifier run was performed. All branches remain local for this task.

Evidence: plugin repositories `build/logs/constructor-enum-*`,
`build/reports/constructor-enum-{unit,platform,plugin-tests}/` as applicable;
analyzer work `build/logs/constructor-enum-*`; standalone checkout `build/logs/`;
main analyzer `build/investigations/constructor-enum/{packaged139,packaged140}/`
(including `packaged140/diagnostic-diff.json`). Archive audit is FDE
`build/logs/constructor-enum-packages.json`.

Artifacts:

- `upstream/Intellij-EmmyLua2/build/distributions/IntelliJ-EmmyLua2-0.30.0-140-IDEA262-patched-modules.zip`
- `intellij-factorio/build/distributions/intellij-factorio-0.7.7-dev.zip`

ZIP SHA-256:

- EmmyLua2: `8bd8744807452933a16b190b788aba9e49fca2dccd3aca5be4f338c0d16c7257`.
- FDE: `4929d27ccc8d9a76d04806a1edfb677405128af3cb7fb67fce7fc240f1f382dc`.

Arm64 analyzer SHA-256: `7256ebb3e2de790a227cbd4af0ea89197ffd76e6214ef92d81b9b6618f249f7e`.

## Build 141 — computed-index assignment flow (2026-10-13)

The analyzer pin is `c29ce116c7eebeb46a54b0f1b42d862b8af3271c` on
`work/intellij-modules`. This release narrows reads such as `groups[key]` after
`groups[key] = groups[key] or {...}`. It preserves the declared element type while
removing nil when the same local table/key definitely received a nonnil value.
Key/table reassignment, alias-capable writes, calls, control-flow joins, loop and
closure boundaries conservatively stop the new trace. Nominal classes retain
their existing read/write semantics.

The independent local branch is `fix/computed-index-assignment-flow`, signed
`9fdb3dde`, on current upstream main `cb05b0c0`; signed work backport `3ee94cbc`.
Its reproduction uses upstream's strict array indexing and does not depend on
the pending strict dictionary-index feature. Work-only integration tests verify
all combinations of strict.tableIndex and strict.arrayIndex, precise element
type and retained unsafe-lookup warnings. No new branch or PR was pushed.

This build also includes the reviewed follow-ups to dictionary constructors
(PR1291, work `e538efd3`) and cross-file enum tables (PR1292, work `6ad16119`).
Existing module, metatable, read/write, optional-key, cast and type-display fixes
remain. Toolkit and LSP4IJ dependencies are unchanged.

Validation completed:
- Standalone upstream workspace: 2,407 tests, all features; workspace/all-target
  Clippy with warnings denied and rustfmt pass.
- Combined work workspace: 2,526 tests, all features; the same Clippy/fmt checks.
- EmmyLua2: 22 tests; FDE: 59 unit tests passed, one optional native-game skip.
- Packaged server protocol replay uses identical copies of all 37 Lua files from
  LogisticTrainNetwork. Only the reported line432 NeedCheckNil disappears:
  request_processor.lua 46→45, constants.lua 0→0, cache.lua 8→8. No diagnostics
  added in the three checked files. Original mod sources were not modified.
- Archive audit verifies plugin IDs/versions, source pin, matching release/
  prepared/ZIP server bytes and Unix 0755 executable modes.

IntelliJ platform verification: all 9 selected tests passed (8 PerModServersTest
and 1 FactorioLuaLanguageLevelTest).
No interactive IDE/game test or Plugin Verifier run. The custom analyzer remains
macOS arm64 only; other bundled server binaries remain upstream0.25.1.

Evidence:
- Analyzer `build/checkouts/computed-index-flow/build/logs/{workspace-tests,clippy,fmt}.log`.
- Work analyzer `build/logs/computed-index-{workspace-tests,clippy,fmt,integration}.log`.
- Main analyzer `build/investigations/computed-index-flow/{packaged140,packaged141}/`,
  including `packaged141/diagnostic-diff.json`.
- Plugin worktree `build/logs/computed-index-*` and saved
  `build/reports/computed-index-*` XML/HTML reports.
- FDE `build/logs/computed-index-packages.json` archive audit.

Install both archives from their usual directories, then restart IntelliJ:
- `upstream/Intellij-EmmyLua2/build/distributions/IntelliJ-EmmyLua2-0.30.0-141-IDEA262-patched-modules.zip`
- `intellij-factorio/build/distributions/intellij-factorio-0.7.8-dev.zip`

## Build 142: unbound-table field diagnostics (2026-10-13)

Analyzer `6666fb8402124e510642a5dec3ad40f88e014bc8` includes local standalone
`fix/unbound-table-field-diagnostics` (`fb18e8d6`, upstream/main `cb05b0c0`) via
signed backport `3cbe6449`. The undefined-field checker previously assigned
unannotated tables the nearest preceding or same-named class/enum definition.
It now uses the declaration's doc-comment owner. Existing class/enum checks
remain, including differently named runtime tables. This fix is independent of
strict dictionary indexing. No branch or PR was pushed.

Standalone all-feature workspace validation passes 2,405 tests; combined work
passes 2,529. Both pass workspace/all-target/all-feature Clippy with warnings
denied and rustfmt. Emmy's 22 client tests and FDE's 59 unit tests pass (one optional
native-game test skipped). Final platform results are recorded in FDE
`docs/ACCEPTANCE.md`.

A raw-LSP replay against identical copies of all 37 LTN Lua files removes the
false writes at request_processor.lua:475/482/490, plus the same false binding at
926/927. Diagnostics change 45→40; constants remains 0 and cache remains 8. No
new diagnostics in the three checked files. All six `train.id` hovers remain
uint32. Original mods were not modified. Evidence:
- Analyzer `build/investigations/unbound-table-fields/{packaged141,packaged142}/`
  and packaged142/diagnostic-diff.json.
- This worktree `build/logs/unbound-*` and `build/reports/unbound-routing/`.
- FDE `build/logs/unbound-table-package.json` verifies ZIP metadata/pin, executable
  permissions, and identical release/prepared/archive arm64 server bytes.

ZIP SHA-256: `519f7bf56d05ba17867e476490feee650627ce39e6832cbee9aa6bdf103d851f`.
Analyzer SHA-256: `bbc0ddfa4a7619886de31407c60323dec3ca5e53b16080a3169f9fc25c95d3b7`.

Install EmmyLua2 build142 and restart IntelliJ. FDE stays 0.7.8-dev; its existing
archive is unchanged. Analyzer-only releases update FDE's development pin and
integration tests without requiring a new FDE installation. No interactive IDE
or game acceptance was performed; the custom binary remains macOS arm64 only.


## Build 143: nominal enum member compatibility (2026-10-13)

Analyzer `d750819c0f7d37e25e038c38c6542c95fbc4f41f` includes standalone
`fix/nominal-enum-member-compatibility`, signed commit `c6446351` based on
upstream/main `cb05b0c0`. Signed backport `dd161a18` preserves its provenance;
only the work branch's existing union iteration API required adaptation.
Nothing was pushed and no PR was opened.

The rewritten checker omitted enum member-value compatibility. Our existing
inline member-class preservation exposes that omission for Factorio's generated
API. The checker now accepts the enum's actual nominal member classes and their
subclasses without losing precise hover types. It resolves the initializer in
its defining file, preserves private class identity, and rejects unrelated
classes, other enums, nil and unresolved/recursive enum members. Required enum
parameters reject a union containing nil. Strict dictionary indexing is not a
prerequisite; primitive enum compatibility uses its existing paths.

Validation:
- Standalone: 2,409 workspace/all-feature tests; combined work: 2,536. Both pass
  workspace/all-target/all-feature Clippy with warnings denied and rustfmt.
- EmmyLua2: 22 client tests. FDE: 59 unit tests passed, one optional native-game
  test skipped.
- All 9 selected IntelliJ platform tests pass: 8 PerModServersTest cases and
  1 FactorioLuaLanguageLevelTest case.
- Packaged-server protocol replay uses identical copies of all 37 LTN Lua files.
  Only the four ParamTypeMismatch warnings at request_processor.lua:479/487/513/516
  disappear (41→37 diagnostics). cache.lua stays at 8 and constants.lua at 0.
  No new diagnostics; all six front/back hovers preserve their precise member
  classes. Original mod files were not modified.
- Archive audit verifies plugin version, analyzer pin, matching release/prepared/
  archive server bytes and Unix 0755 executable modes. FDE stays 0.7.8-dev and
  its existing archive's SHA-256 and modification time remain unchanged.

Evidence:
- Standalone analyzer `build/logs/standalone-final-{workspace-tests,clippy,fmt}.log`.
- Work analyzer `build/logs/rail-enum-{workspace-tests,clippy,fmt}.log`.
- Standalone checkout `build/investigation/{packaged142-real,packaged143-real}/`,
  including packaged143-real/diagnostic-diff.json.
- Emmy worktree `build/logs/rail-enum-*` and `build/reports/rail-enum-routing`.
- FDE `build/logs/rail-enum-*`, including rail-enum-package.json, and saved
  `build/reports/rail-enum-{unit,platform}` reports.

Artifact:
`upstream/Intellij-EmmyLua2/build/distributions/IntelliJ-EmmyLua2-0.30.0-143-IDEA262-patched-modules.zip`

- Emmy ZIP SHA-256: `2950e3bbd69c24995057296ab636e2e2316818b0fe3025fa9e846f19aa207cbd`.
- Analyzer SHA-256: `f902e64cb59748b2da5aa4f8f263e8ba7420c1268ff56ed0800821dbb929f640`.
- Unchanged FDE ZIP SHA-256: `97f14b6051ebd3ef1565b6b085863a1579b7002abe932c0a054790978127f90b`.

Install only EmmyLua2 build 143 and restart IntelliJ. No FDE reinstall is needed.
No interactive IDE/game test or Plugin Verifier run. The custom analyzer remains
macOS arm64 only; other bundled server binaries remain upstream 0.25.1. Toolkit
and LSP4IJ are unchanged.

## Build 144: callback return aliases and variadic tuple tails (2026-10-13)

LTN `tools.printmsg` rejected callbacks returning `LocalisedString`, including
request_processor.lua502. The higher-order solver left union aliases opaque;
table-to-tuple checking also treated the repeated tail as a required slot.
Both failures reproduce on upstream without the local analyzer patches.

- Local standalone fix/callback-return-type on upstream/main119d0e10:
  signed/verified13cef7ff. No push or PR for this fix yet.
- Signed work backport4acdc110 retains the older union representation.
- Upstream tuple-argument prerequisite3e9bc8d6 is integrated as signed3fde3155,
  with the same assignment context for fixed elements and repeated tails.
- Final signed analyzer pin `f09c538f04fef94371780cf8917f4474f855d5ae`; standalone/work checkouts clean.

The solver expands aliases with cycle/depth guards and checks every possible
actual return against an allowed expected alternative. Repeated tuple tails can
be empty; present numeric entries, required headers and invalid nested values
are checked. Six test groups cover scalar/table callbacks, aliases/chains/unions,
parameter variance, nullable returns, recursive aliases and tuple tails.

Validation:
- All2,408 standalone and2,544 combined workspace/all-feature Rust tests pass,
  plus workspace/all-target/all-feature Clippy(-Dwarnings) and rustfmt.
- Emmy22 tests and FDE59 unit tests pass (one optional native-game skip).
- All9 IntelliJ platform compatibility checks pass. The initial invocation
  omitted -PrealMods and failed only that fixture precondition; its corrected
  rerun with read-only mods_21 sources passes. Both reports are retained.
- Packaged-server replay against the same37saved LTN Lua files removes exactly
  eight msg_func warnings at502/781/798/839/862/894/945/949. request_processor
  diagnostics37->29; cache8, constants0 and tools31 unchanged. No added diagnostics
  or changed sampled hovers in the four checked files.
- ZIP ID/version/source pin, release/prepared/archive binary identity and Unix
  executable modes verified. FDE0.7.8-dev archive hash AND mtime are unchanged.

Logs: standalone build/logs/, work build/logs/callback-return-*, this checkout's
build/logs/callback-return-{build144,routing}.log, FDE's corresponding unit/platform
logs and preserved build/reports/callback-return-* snapshots. Protocol evidence:
analyzer build/investigations/callback-return/{packaged143-real,packaged144-real}/,
including packaged144-real/diagnostic-diff.json. Archive audit:
FDE build/logs/callback-return-package.json.

Install only
`upstream/Intellij-EmmyLua2/build/distributions/IntelliJ-EmmyLua2-0.30.0-144-IDEA262-patched-modules.zip`
and restart IntelliJ. FDE stays0.7.8-dev and needs no reinstall. Only macOSarm64
has the custom analyzer; other bundled binaries remain upstream0.25.1. No ordinary
IDE installation, interactive game test, Plugin Verifier run, or new upstream push.

## Build 145: LTN length, alias, guard and constructor fixes (2026-10-13)

Install **0.30.0-145-IDEA262-patched-modules** and restart IntelliJ. The final
analyzer pin is `f93fb93873e7dcda3f23335c12c7f44709718af6`. FDE stays **0.7.8-dev**:
its development pin and tests advance, while its existing ZIP remains unchanged.

Seven local fix branches are based on freshly fetched upstream/main `119d0e10`.
All source commits and their provenance-preserving work backports are signed.
No new branches were pushed and no PRs were opened for this batch.

| Branch | Standalone commit(s) | Purpose |
| --- | --- | --- |
| `fix/builtin-length-result` | `383bcf93`, `2ed70489` | Infer integer for built-in # and inferred fields assigned its result; preserve custom len operators. |
| `fix/aliased-call-arguments` | `0dbba984` | Match actual aliases against parameter types without losing generic identity or nominal class checks. |
| `fix/aliased-member-discriminants` | `a325c369` | Expand aliased discriminants during condition narrowing. |
| `fix/computed-index-guards` | `817fdd25` | Preserve guards for stable computed array/dictionary reads, with mutation and call barriers. |
| `fix/union-table-constructor-checks` | `a5935c2d` | Validate a whole union alternative instead of selecting an unrelated class or mixing alternatives. |
| `fix/class-index-array-compatibility` | `498c8f49` | Accept compatible numeric index signatures as array elements, including inherited/generic signatures. |
| `fix/inline-assertion-arguments` | `83e7e455` | Honor explicit inline assertions consistently in call argument caches and constructor diagnostics. |

The inline assertion branch stacks PR #1290's existing scope fixes and the class
index/array prerequisite. These must land first before that branch is submitted.
The other branches are independent of one another. The computed guard integration
has extra work-only tests for strict dictionary indexing and asymmetric read/write
contracts; the assertion/union combination has a separate integration test.

Validation:

- All **2,583** final combined Rust workspace/all-feature tests pass; workspace,
  all-target/all-feature Clippy with warnings denied and rustfmt pass. Each
  standalone branch also passes its applicable full suite and static checks.
- EmmyLua2: **22** client tests. FDE: **59** unit tests passed, one optional
  native-game test skipped; **9** IntelliJ platform tests passed with the final
  prepared server and the read-only mods_21 fixture.
- The final packaged server was replayed against identical copies of all **37**
  LTN Lua files. All four reported sites are clear: #selected_trains is integer;
  merge_item_info is SignalID? before its guard and SignalID on guarded access;
  nested Provided lookups retain their guards; the cast LocalisedString constructor
  has no spurious injection/missing-field/type errors.
- request_processor.lua diagnostics fall **29→9**; total saved-mod diagnostics
  fall **1,836→962**. The source mod was not edited. The replay does not establish
  that all remaining diagnostics are correct or that the whole mod is clean.
- Archive checks verify plugin ID/version, source pin, identical release/prepared/
  archive binaries, and Unix 0755 server permissions. FDE ZIP SHA-256 and mtime
  remain unchanged. No interactive IDE/game test or Plugin Verifier run.

Known follow-ups: overloaded on_event calls at init.lua:328/337 were already
broken; blindly checking every overload now produces different diagnostics at
those same sites (1,147→466 combined there). Correct return typing exposes the
broader SignalIDType lookup at request_processor.lua:879. Two string.find guards
at surface-interface.lua:154/173 now expose a pre-existing string-search truthiness
bug: a minimal plain-string input warns with build144 too, while the alias input
was previously hidden by unknown inference. Three uncast LocalisedString
constructors still have nullable-element diagnostics. These are outside the four
reported cases; no broad overload or multi-return patch was added.

Evidence: analyzer `build/investigations/ltn-batch-20261013/` contains the frozen
input manifest, packaged144/packaged145 replay, diagnostic-diff.json, preliminary
replay and retained binary, OVERLOAD_FOLLOWUP.md, and string-find-before/after.
Analyzer work logs are `build/logs/ltn-batch-*`. Emmy/FDE logs and preserved reports
are also under `build/logs/ltn-batch-*` and `build/reports/ltn-batch-*`; FDE's
`build/logs/ltn-batch-package.json` records archive verification.

- ZIP SHA-256: `9e125a2a215e2be64e148d376bb0b16d4f21f16bf4b8a1bd24cace22b6fc8dbf`.
- Analyzer SHA-256: `7f9f185fe0ccaba672702e6405b695c71e1f2fc87d607982370a7b54219b5d0a`.
- Unchanged FDE ZIP SHA-256: `97f14b6051ebd3ef1565b6b085863a1579b7002abe932c0a054790978127f90b`.

The custom analyzer fixes apply to macOS arm64 only. Other packaged server
binaries remain upstream 0.25.1. Toolkit and LSP4IJ dependencies are unchanged.

## Build 148: named union parameter aliases (2026-10-13)

UP-041 fixes valid enum/class arguments rejected when a parameter's union is
hidden behind a named alias. `LuaEventType` explicitly includes `defines.events`,
so both LTN custom-event calls at request_processor.lua:811 and :910 are valid.
The parameter matcher now expands these aliases and checks every argument
alternative against an accepted union member. Cycle/depth guards bound recursive
aliases; wrong literals, incompatible types and disallowed nil remain errors.
General compatibility, cast, assignment and strict subtype rules are unchanged.

Standalone branch `fix/union-alias-parameters` has signed commit `20c29828` on
upstream `a0ce1512`. Signed cherry-pick `f0413972` integrates it into the work
branch; final analyzer pin is `c35d8ae451c11f8b493ef0e0380c29a45f6aa353`.
It has no pending-fix prerequisite, including UP-016/UP-040. No push or PR.

Validation:

- Five regression groups pass, including alias chains, source unions, nullable
  and incompatible arguments, literal alternatives and recursive aliases.
- Standalone workspace/all-feature suite: 2,417 tests passed. Combined work suite:
  2,596 passed. Both pass Clippy with warnings denied and formatting checks.
- Packaged-server replay removes exactly the two reported LTN diagnostics; three
  unrelated diagnostics remain, with no new diagnostics. The minimal replay
  accepts enum/class cases and retains the invalid-boolean diagnostic.
- Three FDE platform tests pass: generated API definitions with separate
  workspaces, asymmetric API fields, and Lua runtime selection.
- Packaging verifies descriptor/version, analyzer pin/hash, prepared/archive
  identity and executable mode 0755. All 23 existing FDE ZIPs retain their hashes
  and modification times; FDE stays at 0.7.8-dev with a development pin update.

ZIP SHA-256: `a812a1c60a60a04c46a120ed5d339171157bc2f9cdb1aef81f9b317bb4e83a03`.
Analyzer SHA-256: `3e7131f3d17c84a2e40268c1d7ae1c1597fba84dde21deed10f127ac8020ddd3`.
Custom analyzer changes apply only to macOS arm64. Java client code is unchanged;
no interactive IDE validation or regular-profile installation was performed.
Build 146's two file-event/RPC timeout cases were not retested or fixed.
Evidence: analyzer `build/investigations/event-type-union-20261013/` and repository
`build/logs/` directories. All task commits are signed and remain local.

## Build 147: numeric aliases and nullable dictionary writes (2026-10-13)

Install **0.30.0-147-IDEA262-patched-modules** and restart IntelliJ. Only EmmyLua2
needs updating; FDE remains 0.7.8-dev. Its development analyzer pin was updated,
but its existing ZIP was not rebuilt. The regular IDE profile was not modified.

Analyzer pin: `210923cfd388f3559652c55d1fb1da96490f4bb0`.
The canonical workspace inventory now assigns permanent identifiers:

- **UP-016 — `fix/nullable-table-index`:** standalone regression `ba8080c2`
  records the old branch's existing nullable-write contract. Work correction
  `4bee6720` fixes its semantic-rewrite port: dictionary writes accept T or nil,
  while wrong non-nil types, required fields and directional write contracts
  remain checked. The old contribution branch still needs porting before a PR.
- **UP-040 — `fix/numeric-alias-arithmetic`:** standalone `bb9e92f7`, based on
  upstream `a0ce1512`, recognizes integer aliases in addition/subtraction/
  multiplication. Work cherry-pick `34a147b2` retains provenance. Mixed numeric
  operands, nullable values, division/power and custom operators retain their
  existing behavior. `d56f66df` adds the combined LTN regression.

All new commits are signed and local; no upstream PR or push was requested.

Validation:

- Standalone arithmetic: all 1,453 code-analysis tests, including five new
  regression groups, plus workspace/all-target Clippy and formatting.
- Original dictionary branch: all 11 table-index tests.
- Combined analyzer: all 2,591 workspace/all-feature tests, Clippy and formatting.
- Packaged-server replay of a fixed LTN snapshot: warnings at 667, 679, 698 and
  699 are gone. Diagnostics fell from nine to four with no newly introduced
  diagnostics. The remaining event, return-type and SignalID indexing reports
  are separate issues.
- Three IntelliJ platform checks passed: generated API definitions in separate
  workspaces, asymmetric API attributes, and Lua runtime selection.
- Packaging verified the plugin version, analyzer pin/hash and executable 0755
  modes. FDE's existing archive and four unrelated uncommitted nightly-pin/docs
  files were verified unchanged by SHA-256.

This validation does not retest build 146's two file-notification/RPC timeout
cases or claim that those existing limitations are resolved. Java plugin code
is unchanged; there was no interactive IDE test.

Evidence: analyzer `build/investigations/numeric-assignments-20261013/`, including
`diagnostic-diff.json`, `package-verification.json`, and `fix-logs/`. Build/test
logs also remain under the EmmyLua2 and FDE repositories' `build/logs/` folders.

## Build 146: upstream selector merge and official LSP4IJ (2026-10-13)

Rebased `work/patched-build` from upstream `ee211107` onto `a3a78d9`.
The merged default runtime selector is now supplied by upstream PR #63; its
local equivalent `1678db5` was dropped. All 41 other commits were retained and
re-signed. The routing contribution is the only remaining product source change
relative to upstream; its work copy is `2334790`, while open PR #60 remains at
`b9f1ce9`. Local packaging, analyzer pins and history were preserved.

The rebase's Git tree is identical to the prior work head `1cb15df` before the
build-number/documentation updates. All 1,383 packaged class files also match
build 145 byte-for-byte. The analyzer pin and binary remain unchanged. A recovery
branch, `backup/patched-build-before-selector-cleanup-20261013`, retains `1cb15df`.
The rewritten work branch has not been pushed; contribution branches were not
rewritten or deleted.

Build 146 uses official LSP4IJ nightly `0.21.1-20261011-014957`. Release packaging
passed descriptor/version checks, analyzer pin/hash checks, prepared/archive
identity, and Unix server executable permissions. All 22 EmmyLua2 tests passed.

FDE integration validation is limited: seven of nine selected platform tests
passed, including formatting, real-mod navigation and Lua-version selection. The
saved dependency disk-replacement and locale-file deletion checks timed out in
two runs, including one with a fresh isolated test cache. A comparison with the
unchanged build 145 also failed the dependency test (a completion RPC timeout).
No product or test source was changed to suppress these failures. This does not
establish their precise cause; treat file-notification/test stability as a
separate follow-up. No interactive IDE/game validation or regular installation
was performed. FDE's existing uncommitted changes and 0.7.8-dev ZIP were preserved.

Installable archive:
`$WORKSPACE/upstream/Intellij-EmmyLua2/build/distributions/IntelliJ-EmmyLua2-0.30.0-146-IDEA262-patched-modules.zip`

- ZIP SHA-256: `08952af104f00595eb6bda47c5609871cfc7cc5e373e86f836906ac41f79ec60`.
- Analyzer SHA-256: `7f9f185fe0ccaba672702e6405b695c71e1f2fc87d607982370a7b54219b5d0a`.
- Rebase/signature, package and class comparison evidence:
  `build/logs/upstream-cleanup-20261013/`.
- FDE initial, fresh-cache and baseline reports:
  `$WORKSPACE/intellij-factorio/build/logs/emmy-upstream-cleanup-20261013/`.

Six merged contribution branches can be retired: EmmyLua2's architecture,
legacy-global parsing, code-style and default-version branches, plus LSP4IJ's
breakpoint-removal and formatting branches. LSP4IJ's obsolete work branch can
also be retired after the official nightly adoption. Keep EmmyLua2's open
routing branch and active work branch. No deletions were performed.
