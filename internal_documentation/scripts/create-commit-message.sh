#!/bin/bash
# Create a commit message: <risk char> <intention char> <message>
#
# Usage: create-commit-message.sh RISK INTENTION MESSAGE
# See internal_documentation/processes/ArloCommitNotation.process.md for the codes.
set -e

name=$(basename "$0")

fail() {
    echo "$name: error: $1" >&2
    exit 1
}

if [ $# -ne 3 ]; then
    echo "$name: usage: $name risk intention message" >&2
    exit 2
fi

risk=$1
intention=$2
message=$3

case "$risk" in
    completely_safe) risk_char=. ;;
    proven) risk_char=- ;;
    single_item) risk_char=! ;;
    other) risk_char=@ ;;
    *) fail "unrecognized risk '$risk' (expected one of: completely_safe, proven, single_item, other)" ;;
esac

case "$intention" in
    environment) intention_char=e ;;
    test) intention_char=t ;;
    refactor) intention_char=r ;;
    feature) intention_char=F ;;
    bug) intention_char=B ;;
    documentation) intention_char=d ;;
    *) fail "unrecognized intention '$intention' (expected one of: environment, test, refactor, feature, bug, documentation)" ;;
esac

# Development environment changes can't break production, whatever the testing.
if [ "$intention" = environment ] && [ "$risk" != completely_safe ]; then
    fail "intention '$intention' can't break production, so risk must be completely_safe"
fi

# Trim leading and trailing whitespace.
message="${message#"${message%%[![:space:]]*}"}"
message="${message%"${message##*[![:space:]]}"}"
[ -n "$message" ] || fail "message is required"

echo "$risk_char $intention_char $message"
