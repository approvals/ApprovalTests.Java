# Architecture

This document describes the high-level architecture of ApprovalTests.Java.
If you want to familiarize yourself with the codebase, this is a good place to start.

## Bird's Eye View

ApprovalTests is a library for unit testing that replaces hand-written assertions with a "golden master" approach:
test output is saved to a file, and future test runs are compared against that saved snapshot.
When a test produces new output, a **Writer** serializes it to a `.received.` file.
A **Namer** determines the file paths for both `.received.` and `.approved.` files (derived from the test class and method names).
An **Approver** compares the two files, and if they differ, a **Reporter** shows the difference (typically by launching a diff tool).

The user's main entry point is `Approvals.verify()`.

## Module Structure

The project is a multi-module Maven build. The two library modules that ship to users are `approvaltests-util` and `approvaltests`.
The two `-tests` modules and `counter_display` are never published.

```
approvaltests-util          Shared utilities (no dependency on approvaltests)
approvaltests               Core approval testing library (depends on approvaltests-util)
approvaltests-util-tests    Tests for approvaltests-util
approvaltests-tests         Tests for approvaltests
counter_display             Internal developer tool
```

## `approvaltests-util`

This module contains general-purpose utilities under two root packages:

- **`com.spun.util`** — Helper classes for strings, arrays, JSON, IO, reflection, and more. These are used heavily throughout `approvaltests` but have no dependency on it. Key types: `ObjectUtils`, `StringUtils`, `ArrayUtils`, `JsonUtils`, `FileUtils` (in `io/`).

- **`org.lambda`** — A lightweight functional programming library providing `Function0`–`FunctionN`, `Action0`–`ActionN`, and `Query` (a collection-querying API used in place of Java Streams throughout the codebase).

- **`org.packagesettings`** — A small framework for hierarchical configuration via `PackageSettings.java` files placed in Java packages. `PackageLevelSettings` walks the package hierarchy to resolve settings.

## `approvaltests`

This is the core library. Everything lives under `org.approvaltests`. The main concepts map to subpackages:

### `core/`

Defines the key interfaces and types that the rest of the system plugs into:

- **`ApprovalWriter`** — interface for writing test output to a `.received.` file.
- **`ApprovalFailureReporter`** — interface invoked when a test fails (received ≠ approved).
- **`Scrubber`** — interface for transforming output before comparison (e.g., replacing dates or GUIDs with deterministic placeholders).
- **`Options`** — the main configuration object, carrying a scrubber, reporter, file extension, namer, writer, and comparator. Immutable-style builder via `with*()` methods.
- **`Verifiable`** — interface for objects that know how to verify themselves.

### `writers/`

Implementations of `ApprovalWriter`. `ApprovalTextWriter` handles the common text case; others handle binary files, images, XML, AWT components, and directory-to-directory comparisons. `ApprovalWriterFactory` / `DefaultApprovalWriterFactory` control which writer `Options` uses by default.

### `namer/`

Implementations of `ApprovalNamer`. `StackTraceNamer` is the default — it inspects the call stack to derive `ClassName.methodName` and the source directory. `NamerFactory` and `NamerFactoryForOptions` provide labelling (e.g., parameterized test names). `ApprovalResults` offers static helpers for customizing the namer from within a test.

### `approvers/`

`ApprovalApprover` is the interface for the approve/report lifecycle (`approve()`, `reportFailure()`, `cleanUpAfterSuccess()`). `FileApprover` is the standard implementation — it orchestrates the core verify loop: write the received file, compare it with the approved file, and report on failure. `ApprovalTracker` detects duplicate `verify()` calls in a single test.

### `reporters/`

A large family of `ApprovalFailureReporter` implementations. Each one knows how to launch a specific diff tool (Beyond Compare, IntelliJ, VS Code, etc.) or perform a specific action (clipboard, JUnit assert, auto-approve).

- `DiffReporter` — the default, tries a platform-appropriate list of diff tools.
- `FirstWorkingReporter` — tries reporters in order until one succeeds.
- `UseReporter` — annotation for selecting a reporter at the test/class/package level.
- `ReporterFactory` resolves the active reporter by checking `@UseReporter` annotations, then `PackageSettings`, then falling back to `DiffReporter`.
- Platform subdirectories (`windows/`, `macosx/`, `linux/`, `intellij/`) contain OS-specific diff tool integrations.

### `scrubbers/`

Implementations of `Scrubber`: `DateScrubber`, `GuidScrubber`, `RegExScrubber`, `NormalizeSpacesScrubber`, and `Scrubbers` (a static utility combining them).

### `combinations/`

`CombinationApprovals` — verifies a function against all combinations of input parameters. The `pairwise/` subpackage provides pairwise combination reduction.

### `inline/`

An alternative approval mode where the expected value lives inside the test source code (as a string literal) rather than in a separate `.approved.` file. Key types: `InlineJavaReporter`, `InlineComparator`, `InlineOptions`.

### `awt/`

`AwtApprovals` — approval testing for graphical AWT/Swing components and animated GIF sequences.

### `legacycode/`

`LegacyApprovals` — utilities for approval-testing hard-to-test legacy code via lock-down tests over input permutations.

### `testcommitrevert/`

`TestCommitRevertRunner` — a test runner for the TCR (Test && Commit || Revert) workflow.

### `integrations/junit5/`

JUnit 5 extensions for approval tests.

### Top-Level Classes

- **`Approvals`** — the primary public API. `verify()`, `verifyAll()`, `verifyAsJson()`, and friends. All overloads ultimately funnel through `verify(ApprovalWriter, Options)`.
- **`JsonApprovals`**, **`JsonJacksonApprovals`**, **`JsonJackson3Approvals`**, **`JsonXstreamApprovals`** — JSON-specific verify methods using different serialization libraries.
- **`StoryBoard`** / **`MarkdownStoryBoard`** / **`StoryBoardApprovals`** — for verifying sequences of state changes (e.g., Game of Life frames).
- **`ReporterFactory`** — resolves which reporter to use for a given test, walking annotations and package settings.
- **`ApprovalSettings`** / **`ApprovalUtilities`** — global settings and helpers.

## The Verify Flow

A call to `Approvals.verify("hello")` proceeds roughly as follows:

1. `Options` creates an `ApprovalTextWriter` (via its `ApprovalWriterFactory`).
2. `Approvals` obtains an `ApprovalNamer` (by default a `StackTraceNamer` that reads the call stack).
3. `Options` scrubs the output through its `Scrubber` (default is `NoOpScrubber`).
4. A `FileApprover` is constructed with the writer, namer, and comparator.
5. `FileApprover.approve()` writes the `.received.` file and compares it with the `.approved.` file.
6. If they differ, `ReporterFactory` resolves the active `ApprovalFailureReporter` and calls `report()`.
7. The reporter (e.g., `DiffReporter`) launches a diff tool or throws an assertion error.

## Test Layout

Tests mirror the main source structure. Approved files (`.approved.txt`, `.approved.json`, etc.) live alongside the test source files and are checked into version control. Received files (`.received.*`) are generated at test time and should not be committed.

Test files are in `approvaltests-tests/src/test/java/org/approvaltests/` and `approvaltests-util-tests/src/test/java/`.
