#!/bin/bash
set -euo pipefail
PLUGIN_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
WORKSPACE="$(cd "$PLUGIN_ROOT/../.." && pwd)"
export GRADLE_USER_HOME="$WORKSPACE/spike/cache/gradle"
export JAVA_HOME="${FMTK_JAVA_HOME:-/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home}"
export TMPDIR="$WORKSPACE/spike/tmp"
export JAVA_TOOL_OPTIONS="-Duser.home=$WORKSPACE/spike/build-home -Djava.io.tmpdir=$TMPDIR"
mkdir -p "$TMPDIR" "$WORKSPACE/spike/build-home"
cd "$PLUGIN_ROOT"
bash gradlew --no-daemon --no-configuration-cache -I scripts/routing-test.init.gradle \
  "-PlocalPlatformPath=${FMTK_IDEA_PATH:-/Users/henning/Applications/IntelliJ IDEA.app}" \
  "-PlocalLsp4ijPath=$WORKSPACE/spike/ide/plugins/lsp4ij" test
