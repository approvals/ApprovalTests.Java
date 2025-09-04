# Repo Setup

## Concept

These are the basic dev scripts that every repo should have. They provide a standardized interface for common development tasks that works across different platforms and environments.

The underlying tool is Mise-en-Place, aka "Mise" (https://mise.jdx.dev/), which manages tools and tasks within the project scope without requiring system-wide installations.

The idea is that you can clone a repo on a clean machine, then run one of these scripts, and it will just work (assuming Mise is already installed).

## Prerequisites

1. **Mise** - The only system requirement. All other tools are managed by Mise per-project.

Mise automatically:
- Installs required tools (defined in `.mise.toml`)
- Creates and manages virtual environments  
- Handles dependencies
- Runs tasks with proper environment setup

## Implemented Scripts

### Core Scripts

1. **`build_and_test.sh/.cmd`** - Runs the full build pipeline including tests, type checking, linting, and integration tests
2. **`format_code.sh`** - Formats code using configured formatters
3. **`run_mdsnippets.sh/.cmd`** - Updates markdown documentation with code snippets

### Script Implementation Details

Prefer to name scripts and tasks using `snake_case`.

**Bash Scripts (.sh):**
- Include error handling (`set -euo pipefail`)
- Check for Mise availability with helpful error messages
- Use `mise task run` to execute configured tasks
- Support parallel task execution where appropriate

**Windows Scripts (.cmd):**
- Use Git Bash to execute the corresponding `.sh` file
- Provide cross-platform compatibility without duplicating logic

### Configuration

Tasks are defined in `.mise.toml`.

**Tool Version Management:**
- Use idiomatic version files (e.g., `.java-version`, `.python-version`) for language runtimes to enable easy overrides in CI matrix testing
- Configure `idiomatic_version_file_enable_tools` in `.mise.toml` to automatically detect these version files
- This allows testing against multiple language versions without modifying the main configuration

**CI Integration:**
- CI workflows can override tool versions by writing to the version files before running tasks (e.g., `echo "11" > .java-version`)
- Use matrix builds to test multiple versions: each matrix job writes its target version to the appropriate file
