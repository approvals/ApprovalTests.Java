#!/usr/bin/env bash
set -euo pipefail

# Source the environment
source ./set_enviroment_for_maven.sh

# Import the key and get its ID
echo "$GPG_PRIVATE_KEY" | gpg --batch --import 2>&1 | grep -E "key|imported" || true

# Get the key ID from the imported key
KEY_ID=$(echo "$GPG_PRIVATE_KEY" | gpg --list-packets 2>/dev/null | grep -E "keyid:" | head -1 | awk '{print $2}')
echo "Testing key ID: ${KEY_ID}"

# Test signing with the specific key
if echo "test" | gpg --batch --yes --passphrase "$GPG_PASSPHRASE" --pinentry-mode loopback --armor --default-key "${KEY_ID}" --sign >/dev/null 2>&1; then
    echo -e "\n✓ Passphrase is correct for key ${KEY_ID}"
    exit 0
else
    echo -e "\n✗ Passphrase is incorrect for key ${KEY_ID}"
    exit 1
fi
