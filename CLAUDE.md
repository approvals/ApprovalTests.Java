# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview



## Architecture

This is a multi-module Maven project with the following key modules:

- **approvaltests**: Main library containing core functionality (`Approvals.java`, writers, reporters, namers)
- **approvaltests-util**: Shared utilities and helper classes
- **approvaltests-tests**: Integration tests and examples
- **approvaltests-util-tests**: Tests for utility classes
- **counter_display**: Sample application
- **html_locker**: Additional tooling

The core API is in `org.approvaltests.Approvals` class, which provides static methods like `verify()`, `verifyAll()`, `verifyAsJson()`. The library uses a pattern of Writers (generate output), Namers (determine file names), and Reporters (show differences when tests fail).

## Common Commands

### Build and Test
```bash
# Set next release version
mvn versions:set -DnewVersion=


# Full build with all tests
mvn install

# Build and test (recommended)
./build_and_test.sh

# Skip tests if some are machine/locale dependent
mvn install -DskipTests

# Run tests for specific module
mvn verify -pl approvaltests-tests/
```

### Development
```bash
# Format code
mvn formatter:format

# Run just the main tests
./run_tests_without_compile_dependencies.sh
```
# Release

Releases are triggered when a new release is created on GitHub. 
See `.github/workflows/release-jreleaser.yml` for the active workflow.
The old workflow (`.github/workflows/release.yml`) is disabled but kept for reference.


## Key Files and Concepts

- **Approved files**: `*.approved.*` files must be committed to source control and treated as binary in git (add `*.approved.* binary` to `.gitattributes`)
- **Received files**: Generated during test failures, showing actual output vs approved
- **Reporters**: Tools that show diffs when tests fail (IntelliJ, Beyond Compare, etc.)
- **Namers**: Determine approval file naming based on test method/class
- **Writers**: Generate the actual content to be approved

## Code Style

The project uses the formatter plugin with configuration in `spun.xml`. Code formatting is enforced via Maven build.
