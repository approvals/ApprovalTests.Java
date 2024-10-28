#!/usr/bin/env bash
set -euo pipefail

mvn -B verify --file pom.xml
