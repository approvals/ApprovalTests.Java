#!/usr/bin/env bash
set -euo pipefail

missing_vars=()
[[ -z "${GPG_PRIVATE_KEY:-}" ]] && missing_vars+=(GPG_PRIVATE_KEY)
[[ -z "${GPG_PASSPHRASE:-}" ]] && missing_vars+=(GPG_PASSPHRASE)
[[ -z "${MAVEN_USERNAME_2025:-}" ]] && missing_vars+=(MAVEN_USERNAME_2025)
[[ -z "${MAVEN_PASSWORD_2025:-}" ]] && missing_vars+=(MAVEN_PASSWORD_2025)
if [[ ${#missing_vars[@]} -ne 0 ]]; then
  echo "Missing required env vars: ${missing_vars[*]}" >&2
  exit 1
fi

echo "********** Setting up gpg"
echo "$GPG_PRIVATE_KEY" | gpg --batch --import
echo "********** Create settings.xml"
java build/CreateSettings.java

mvn clean verify deploy -P release \
  --settings settings.xml \
  -DperformRelease=true \
  -Dgpg.passphrase="$GPG_PASSPHRASE"