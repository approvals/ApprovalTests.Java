# Repo Setup

> For every source repo in the world, you should be able to clone it onto a clean machine and run the "build and test" script at the root, and it just works.

## Concept

These are the basic dev scripts that every repo should have. They provide a standardized interface for common development tasks that works across different platforms and environments.

The underlying tool is Mise-en-Place, aka "Mise" (https://mise.jdx.dev/), which manages tools and tasks within the project scope without requiring system-wide installations.

The idea is that you can clone a repo on a clean machine, then run one of these scripts, and it will just work (assuming Mise is already installed).

## Prerequisites

1. **Mise** - The only system requirement. All other tools are managed by Mise per-project.

Mise:
- Installs required tools (defined in `.mise.toml`)
- Creates and manages Python virtual environments (if needed)
- Runs tasks (in parallel where appropriate)

## 

### Core

1. **`build_and_test`** - Runs the full build pipeline including tests and lint. Also used by CI.
2. **`tidy_code`** - Formats code and applies automatic fixes (e.g. removing unused imports)
3. **`update_docs`** - Updates markdown documentation with code snippets (See https://github.com/SimonCropp/MarkdownSnippets)
4. **`run`** - For projects that aren't a library, run it. (Build as necessary.)
  - For desktop apps, launch the app
  - For web sites, open it in a browser
  - For phone apps, open it in a phone emulator

### Script Implementation Details

Prefer to name scripts and tasks using `snake_case`.

**CRTICIAL**: Scripts should be extremely short, delegating responsibility to better tools like Mise tasks.

**Bash Scripts:**
- `build_and_test` checks for Mise availability and includes shebang and error handling (`set -euo pipefail`)
- Use `mise task --quiet run` to execute configured tasks
- No extension on the file name

**Windows Scripts (.cmd):**
Every batch file should be exactly:
```batch
@"%ProgramFiles%\Git\bin\bash.exe" %~dpn0 %*
```

### Task Configuration

Tasks are defined in `.mise.toml`.

## Tool Version Management

### Primary language/runtime

While most Mise tool configuration belongs in `.mise.toml`, to enable testing against multiple language/runtimes in CI we use "idiomatic version files" (e.g. `.python-version`) to specify the versions of tools that vary in CI.

Configure `idiomatic_version_file_enable_tools` in `.mise.toml` to allow this. Read more here: https://mise.jdx.dev/configuration.html#idiomatic-version-files

### CI Integration

**CRITICAL**: CI implementations should be trivial, often just calling a single script, such that it's easy to reproduce CI results on a local machine.

- CI workflows can override tool versions by writing to the version files before running tasks (e.g., `echo "zulu-11" > .java-version`)
- Use matrix builds to test multiple versions: each matrix job writes its target version to the appropriate idiomatic version file

Outside of CI, a separate GitHub workflow runs `tidy_code` and `update_docs` whenever the main branch changes, to bring things back in to compliance.
