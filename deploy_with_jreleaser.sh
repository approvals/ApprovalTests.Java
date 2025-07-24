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
# Get the key ID from the imported key
KEY_ID=$(echo "$GPG_PRIVATE_KEY" | gpg --list-packets 2>/dev/null | grep -E "keyid:" | head -1 | awk '{print $2}')
echo "Using GPG key: $KEY_ID"
mvn clean deploy -Ppublication -DskipTests -Dmaven.javadoc.failOnError=false -Dgpg.keyname="$KEY_ID" -Dgpg.passphrase="$GPG_PASSPHRASE"

echo "********** Setting JReleaser environment variables"
export JRELEASER_MAVENCENTRAL_USERNAME="$MAVEN_USERNAME_2025"
export JRELEASER_MAVENCENTRAL_PASSWORD="$MAVEN_PASSWORD_2025"
export JRELEASER_GPG_PASSPHRASE="$GPG_PASSPHRASE"
export JRELEASER_GPG_PUBLIC_KEY=$(gpg --armor --export)
export JRELEASER_GPG_SECRET_KEY="$GPG_PRIVATE_KEY"

echo "********** Removing artifacts we don't want to deploy"
rm -rf target/staging-deploy/com/approvaltests/approvaltests-parent
rm -rf target/staging-deploy/com/approvaltests/approvaltests-util-tests
rm -rf target/staging-deploy/com/approvaltests/approvaltests-tests
rm -rf target/staging-deploy/com/approvaltests/CounterDisplay
rm -rf target/staging-deploy/com/approvaltests/HtmlLocker

echo "********** Running JReleaser deploy"
mvn -N jreleaser:deploy