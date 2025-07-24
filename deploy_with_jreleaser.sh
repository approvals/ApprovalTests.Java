#!/usr/bin/env bash
set -euo pipefail

# Check required environment variables
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

echo "********** Staging artifacts with Maven"
mvn clean deploy -Ppublication -DskipTests

echo "********** Setting JReleaser environment variables"
export JRELEASER_MAVENCENTRAL_USERNAME="$MAVEN_USERNAME_2025"
export JRELEASER_MAVENCENTRAL_PASSWORD="$MAVEN_PASSWORD_2025"
export JRELEASER_GPG_PASSPHRASE="$GPG_PASSPHRASE"
export JRELEASER_GPG_PUBLIC_KEY=$(gpg --armor --export)
export JRELEASER_GPG_SECRET_KEY="$GPG_PRIVATE_KEY"

echo "********** Running JReleaser deploy"
mvn jreleaser:deploy