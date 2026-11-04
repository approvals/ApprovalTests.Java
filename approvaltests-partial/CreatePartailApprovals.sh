#!/usr/bin/env bash
set -euo pipefail

# Usage (from your project folder): path/to/approvaltests-partial/CreatePartailApprovals.sh [approvaltests-selection.json]
TOOL_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CONFIG="${1:-approvaltests-selection.json}"

mvn -q -B -f "$TOOL_DIR/pom.xml" compile dependency:build-classpath -Dmdep.outputFile="$TOOL_DIR/target/cp.txt"

java -Dapprovaltests.partial.local="$TOOL_DIR/.local" \
  -cp "$TOOL_DIR/target/classes:$(cat "$TOOL_DIR/target/cp.txt")" \
  org.approvaltests.partial.PartialEvaluationCli build --config "$CONFIG"
