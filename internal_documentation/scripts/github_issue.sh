#!/usr/bin/env bash
set -euxo pipefail

if [ $# -ne 1 ]; then
  echo "Usage: $0 <issue_number>" >&2
  exit 1
fi

gh issue view "$1" --json body