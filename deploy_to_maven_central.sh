#!/usr/bin/env bash
set -euo pipefail

if [[ -z "${GPG_PRIVATE_KEY:-}" || -z "${GPG_PASSPHRASE:-}" || -z "${MAVEN_USERNAME_2025:-}" || -z "${MAVEN_PASSWORD_2025:-}" ]]; then
  echo "usage: GPG_PRIVATE_KEY, GPG_PASSPHRASE, MAVEN_USERNAME_2025, MAVEN_PASSWORD_2025 must be set" >&2
  exit 1
fi

echo "$GPG_PRIVATE_KEY" | gpg --batch --import

java -cp build create_settings

mvn clean deploy -P release \
  --settings settings.xml \
  -DskipTests \
  -Dgpg.passphrase="$GPG_PASSPHRASE" \
  -Dgpg.pinentry-mode=loopback