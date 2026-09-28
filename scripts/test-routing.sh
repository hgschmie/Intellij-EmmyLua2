#!/bin/bash
set -euo pipefail
PLUGIN_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
WORKSPACE="$(cd "$PLUGIN_ROOT/../.." && pwd)"
mkdir -p "$PLUGIN_ROOT/build/logs"
exec > >(tee "$PLUGIN_ROOT/build/logs/test-routing-$(date +%Y%m%d-%H%M%S)-$$.log") 2>&1
export GRADLE_USER_HOME="$WORKSPACE/dev/cache/gradle"
export npm_config_cache="$WORKSPACE/dev/cache/npm"
export JAVA_HOME="${FMTK_JAVA_HOME:-/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home}"
export TMPDIR="$WORKSPACE/dev/tmp"
export JAVA_TOOL_OPTIONS="-Duser.home=$WORKSPACE/dev/build-home -Djava.io.tmpdir=$TMPDIR"
mkdir -p "$TMPDIR" "$WORKSPACE/dev/build-home"
cd "$PLUGIN_ROOT"
bash gradlew --no-daemon --no-configuration-cache -I scripts/routing-test.init.gradle \
  "-PlocalPlatformPath=${FMTK_IDEA_PATH:-/Users/henning/Applications/IntelliJ IDEA.app}" \
  "-PlocalLsp4ijPath=$WORKSPACE/dev/plugins/lsp4ij" test
