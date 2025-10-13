#!/bin/bash -euo pipefail

# Check if a commit message was provided
if [ -z "$1" ]; then
  echo "Error: Commit message is required."
  echo "Usage: $0 \"<commit message>\""
  exit 1
fi

# Get the commit message
COMMIT_MESSAGE="$1"

# Add all changes to staging
git add .

# Commit the changes
git commit -m "$COMMIT_MESSAGE"

echo "Commited"
exit 0