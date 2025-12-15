# ApprovalTests.Java Agent Instructions

## Project Overview

ApprovalTests.Java is a snapshot-based testing library that simplifies assertions by comparing expected output to approved files. The library supports JUnit 3/4/5 and TestNG, and works with JDK 1.8-25.

## Architecture

This is a multi-module Maven project with the following key modules:

- **approvaltests**: Main library containing core functionality (`Approvals.java`, writers, reporters, namers)
- **approvaltests-util**: Shared utilities and helper classes (used as a dependency by approvaltests)
- **approvaltests-tests**: Integration tests and examples
- **approvaltests-util-tests**: Tests for utility classes
- **counter_display**: Sample application
- **html_locker**: Additional tooling

The core API is in `org.approvaltests.Approvals` class, which provides static methods like `verify()`, `verifyAll()`, `verifyAsJson()`. The library uses a pattern of Writers (generate output), Namers (determine file names), and Reporters (show differences when tests fail).

## Common Commands

### Build and Test
```bash
# Build and test via Mise (requires mise installed)
./build_and_test
```

**Note:** The project uses [Mise](https://mise.jdx.dev/) for task automation. The `.mise.toml` file defines tasks like `build_and_test` and `format`. Java version is specified in `.java-version` file.

## Key Files and Concepts

- **Approved files**: `*.approved.*` files must be committed to source control. These are automatically treated as binary in git via `.gitattributes` (`*.approved.* binary diff`)
- **Received files**: `*.received.*` files generated during test failures, showing actual output vs approved. These should not be committed
- **Reporters**: Tools that show diffs when tests fail (IntelliJ, Beyond Compare, etc.). Located in `org.approvaltests.reporters`
- **Namers**: Determine approval file naming based on test method/class. Located in `org.approvaltests.namer`
- **Writers**: Generate the actual content to be approved. Located in `org.approvaltests.writers`

## Running Single Tests

Individual test methods can be run using standard Maven/IDE approaches:
```bash
mvn test -Dtest=ClassName#methodName
```

**Note:** Tests run with `en_US` locale by default (configured in surefire plugin) to ensure consistent output across different environments.

## Code Style

There is an automatic code formatter that is used every time you build the project.
