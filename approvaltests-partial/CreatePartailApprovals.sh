#!/usr/bin/env bash
set -euo pipefail

# Usage (from your project folder):
#   path/to/approvaltests-partial/CreatePartailApprovals.sh [--current] [approvaltests-selection.json]
#   path/to/approvaltests-partial/CreatePartailApprovals.sh explain [--current] <class or package prefix> [approvaltests-selection.json]
# --current builds from the latest master on GitHub instead of the approvaltestsVersion tag.
TOOL_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CURRENT=()
ARGS=()
for arg in "$@"; do
  if [ "$arg" = "--current" ]; then
    CURRENT=(--current)
  else
    ARGS+=("$arg")
  fi
done
if [ "${ARGS[0]:-}" = "explain" ]; then
  COMMAND=(explain ${CURRENT[@]+"${CURRENT[@]}"} --config "${ARGS[2]:-approvaltests-selection.json}" "${ARGS[1]}")
else
  COMMAND=(build ${CURRENT[@]+"${CURRENT[@]}"} --config "${ARGS[0]:-approvaltests-selection.json}")
fi

mvn -q -B -f "$TOOL_DIR/pom.xml" compile dependency:build-classpath -Dmdep.outputFile="$TOOL_DIR/target/cp.txt"

java -cp "$TOOL_DIR/target/classes:$(cat "$TOOL_DIR/target/cp.txt")" \
  org.approvaltests.partial.PartialEvaluationCli "${COMMAND[@]}"
