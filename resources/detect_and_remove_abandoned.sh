#!/bin/bash

# Define paths
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LOG_FILE="$SCRIPT_DIR/.approved_files.log"
BASE_DIR="$(realpath "$SCRIPT_DIR/..")"

# Check if the log file exists
if [[ ! -f "$LOG_FILE" ]]; then
  echo "Error: Log file .approved_files.log not found in script directory."
  exit 1
fi

# Find all *.approved.* files recursively from the base directory
echo "Scanning for approval files in base directory: $BASE_DIR..."
ALL_APPROVAL_FILES=$(find "$BASE_DIR" -type f -name "*.approved.*")

# Resolve paths in .approved_files.log
echo "Resolving paths from .approved_files.log..."
RESOLVED_USED_FILES=()
while IFS= read -r LINE; do
  RESOLVED_PATH=$(realpath "$LINE")
  RESOLVED_USED_FILES+=("$RESOLVED_PATH")
done < "$LOG_FILE"

# Identify abandoned files
echo "Identifying abandoned approval files..."
ABANDONED_FILES=()
for FILE in $ALL_APPROVAL_FILES; do
  RESOLVED_FILE=$(realpath "$FILE")
  if ! [[ " ${RESOLVED_USED_FILES[@]} " =~ " $RESOLVED_FILE " ]]; then
    ABANDONED_FILES+=("$RESOLVED_FILE")
  fi
done

# Display the abandoned files
if [[ ${#ABANDONED_FILES[@]} -eq 0 ]]; then
  echo "No abandoned approval files found."
  exit 0
fi

echo "The following approval files are abandoned:"
for FILE in "${ABANDONED_FILES[@]}"; do
  echo "  $FILE"
done

# Ask the user if they want to delete the abandoned files
read -p "Would you like to delete these files? (y/n): " RESPONSE
if [[ "$RESPONSE" == "y" || "$RESPONSE" == "Y" ]]; then
  for FILE in "${ABANDONED_FILES[@]}"; do
    rm -f "$FILE" && echo "Deleted: $FILE"
  done
  echo "All abandoned files deleted."
else
  echo "No files were deleted."
fi

exit 0
