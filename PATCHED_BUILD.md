# Local patched build

Use `work/patched-build` in `$WORKSPACE/upstream/Intellij-EmmyLua2`, where
`WORKSPACE` is `~/ai/fmtk`. The current development package is
`0.25.1-117-IDEA262-patched-modules`. Its plugin ID is unchanged, so installing
this ZIP updates the existing EmmyLua2 plugin.

## Prerequisites and layout

- macOS arm64, Cargo/Rust, Python 3, and JDK 25.
- IntelliJ IDEA at `/Users/henning/Applications/IntelliJ IDEA.app` by default.
  Set `FMTK_IDEA_PATH` to use another local installation.
- JDK at `/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home` by
  default. Set `FMTK_JAVA_HOME` to use another JDK.
- Analyzer checkout at `$WORKSPACE/upstream/emmylua-analyzer-rust`, on
  `work/intellij-modules`. Its HEAD must match [analyzer.lock](analyzer.lock),
  with no tracked changes. `FMTK_ANALYZER_PATH` can select a separate checkout
  of the same pinned revision, and `CARGO_TARGET_DIR` can select its build cache.
  The current pin is
  `df78c6b02be03b7a0fd3cc6d0b44561c1aef3de3`.
- Prepared LSP4IJ at `$WORKSPACE/dev/plugins/lsp4ij`. The current development
  dependency is `0.21.1-SNAPSHOT-factorio-patched`; see the Factorio plugin's
  [development layout](../../intellij-factorio/docs/DEVELOPMENT_LAYOUT.md).

Shared Cargo, Gradle and npm caches live in `$WORKSPACE/dev/cache/`.
The build home is `$WORKSPACE/dev/build-home`, and temporary files use
`$WORKSPACE/dev/tmp`. Logs and test reports belong in each repository's
`build/` directory. Rust compilation artifacts remain in the analyzer's
`target/` directory.

## Build and install

```sh
cd ~/ai/fmtk/upstream/Intellij-EmmyLua2
bash scripts/build-module-patch.sh
```

The script builds the pinned analyzer in release mode, packages the IntelliJ
plugin, checks the ZIP's version and bundled analyzer hash/source pin, and
refreshes the prepared plugin used by Factorio development builds.

Outputs relative to this repository:

- Installable ZIP:
  `build/distributions/IntelliJ-EmmyLua2-0.25.1-117-IDEA262-patched-modules.zip`
- Prepared dependency: `build/prepared/IntelliJ-EmmyLua2`
- Build logs: `build/logs/build-module-patch-*.log`

Install the ZIP using **Settings → Plugins → Install Plugin from Disk**, then
restart IntelliJ. The prepared dependency is a generated build output, not a
second source checkout.

The custom analyzer fixes apply to the **macOS arm64 binary only**. Other
platform binaries remain upstream 0.25.1. The archive includes
`server/darwin-arm64/analyzer.lock` for provenance.

Use the packaging script for the development build: a direct `buildPlugin`
without `localAnalyzerPath` uses upstream analyzer binaries and the `-patched`
version suffix. The script fixes the build number at `117`; overriding
`RUNNER_NUMBER` in the calling environment does not change it.

## Included patches

- macOS architecture selection: JVM names `aarch64` and `arm64` select the
  bundled `darwin-arm64/emmylua_ls` server.
- File-aware server routing: independent branch `fix/file-language-server-routing`.
  The [extension contract](docs/server-routing.md) allows the Factorio plugin's
  per-module server manager to claim files and route gutter requests.
- Legacy Lua member names: independent branch `fix/global-member-name`, commit
  `cf55a94`, permits expressions such as `settings.global[name]` while retaining
  Lua 5.5 global declarations. Development contains the original commit `6893a12`.
- Analyzer module imports: independent analyzer branch
  `fix/workspace-relative-module-resolution`, merged into `work/intellij-modules`.
- Analyzer diagnostic refresh: independent analyzer branch
  `fix/cross-file-diagnostic-refresh`, commit `69b1c31a`, also merged into
  `work/intellij-modules`. See [details and validation](docs/diagnostic-refresh.md).

The analyzer review branches are independent of each other. Keep local package
versions, source pins and build-layout changes off those contribution branches.

## Tests and validation

```sh
cd ~/ai/fmtk/upstream/Intellij-EmmyLua2
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
