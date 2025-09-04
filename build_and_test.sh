#!/usr/bin/env bash
set -euo pipefail

# Check if Mise is available
if ! command -v mise &> /dev/null; then
    echo "❌ Mise is required but not installed. Please install Mise: https://mise.jdx.dev/"
    exit 1
fi

mise task run build_and_test
