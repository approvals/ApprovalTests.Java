#!/usr/bin/env bash
set -euo pipefail

# Usage (from your project folder):
#   path/to/approvaltests-partial/CreateSelectionJson.sh [approvaltests-selection.json]
# Scans the .java files from the current folder down for ApprovalTests usages and writes the selection file.
# An existing selection file keeps its runtimeDiscoveries and output sections; version and entryPoints are replaced.
TOOL_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

mvn -q -B -f "$TOOL_DIR/pom.xml" compile dependency:build-classpath -Dmdep.outputFile="$TOOL_DIR/target/cp.txt"

java -cp "$TOOL_DIR/target/classes:$(cat "$TOOL_DIR/target/cp.txt")" \
  org.approvaltests.partial.PartialEvaluationCli detect --config "${1:-approvaltests-selection.json}"
