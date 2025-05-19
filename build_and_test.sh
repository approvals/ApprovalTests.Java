#!/usr/bin/env bash
set -euo pipefail

TMP_OUTPUT=$(mktemp)
if mvn -B verify --file pom.xml > "$TMP_OUTPUT" 2>&1; then
  # Extract the number of tests run from Maven output
  TESTS_LINE=$(awk '/Results:/ {found=1; next} found' "$TMP_OUTPUT" | grep -Eo 'Tests run: [0-9]+' | head -n 1)
  TESTS_PASSED=$(echo "$TESTS_LINE" | grep -Eo '[0-9]+')
  echo "✅ Built: $TESTS_PASSED tests passed."
  EXIT_CODE=0
else
  cat "$TMP_OUTPUT"
  echo "❌ Build failed."
  EXIT_CODE=1
fi
rm "$TMP_OUTPUT"
exit $EXIT_CODE