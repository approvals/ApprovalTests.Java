#!/usr/bin/env bash


LOGFILE="../approvaltests-tests/.approval_tests_temp/.failed_comparison.log"

while IFS= read -r line; do
  # 1) Parse the line to get source (before '->') and destination (after '->')
  #    Using parameter expansion to split on the substring "->"
  src="${line%->*}"
  dst="${line#*->}"

  # 2) Trim leading/trailing whitespace from src and dst
  #    (xargs does a quick trim when there's no other transformation)
  src="$(echo "$src" | xargs)"
  dst="$(echo "$dst" | xargs)"

  # 3) Perform the move
  echo "Moving '$src' to '$dst'"
  mv "$src" "$dst"
done < "$LOGFILE"