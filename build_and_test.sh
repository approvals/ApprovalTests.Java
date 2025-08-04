#!/usr/bin/env bash
set -euo pipefail

TMP_OUTPUT=$(mktemp)
if mvn -B verify --file pom.xml > "$TMP_OUTPUT" 2>&1; then
  # Extract the number of tests run from Maven output
  TESTS_TOTAL=$(awk '
    /Results:/ {
      for(i=0;i<3;i++) {
        if(getline>0) {
          if($0 ~ /Tests run: [0-9]+/) {
            line = $0
            sub(/.*Tests run: /, "", line)
            sub(/[^0-9].*$/, "", line)
            sum += line
            break
          }
        }
      }
    }
    END {print sum}' "$TMP_OUTPUT")
  
  # Count unique Maven warnings
  WARNING_COUNT=$(grep -E "^\[WARNING\]" "$TMP_OUTPUT" | sort -u | wc -l | tr -d ' ')
  
  echo "✅ Built: $TESTS_TOTAL tests passed, $WARNING_COUNT unique Maven warnings."
  EXIT_CODE=0
else
  cat "$TMP_OUTPUT"
  echo "❌ Build failed."
  EXIT_CODE=1
fi
rm "$TMP_OUTPUT"
exit $EXIT_CODE