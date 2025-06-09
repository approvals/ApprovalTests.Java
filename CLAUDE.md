# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ApprovalTests.Java is a snapshot-based testing library that simplifies assertions by comparing expected output to approved files. The library supports JUnit 3/4/5 and TestNG, and works with JDK 1.8-25.

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

## Key Files and Concepts

- **Approved files**: `*.approved.*` files must be committed to source control and treated as binary in git (add `*.approved.* binary` to `.gitattributes`)
- **Received files**: Generated during test failures, showing actual output vs approved
- **Reporters**: Tools that show diffs when tests fail (IntelliJ, Beyond Compare, etc.)
- **Namers**: Determine approval file naming based on test method/class
- **Writers**: Generate the actual content to be approved

## Running Single Tests

Individual test methods can be run using standard Maven/IDE approaches:
```bash
mvn test -Dtest=ClassName#methodName
```

## Code Style

The project uses the formatter plugin with configuration in `spun.xml`. Code formatting is enforced via Maven build.
