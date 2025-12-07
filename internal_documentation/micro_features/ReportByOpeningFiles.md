# `ReportByOpeningFiles` reporter

The `ReportByOpeningFiles` class provides a simple mechanism to open approved and received files using the system's default application for their file type.

## Purpose
Allows users to visually inspect the received and approved files by automatically launching them on failure. This is useful when the file type corresponds to a tool that provides a good viewing or diffing experience, or as a fallback reporter.

## Features
- **Automatic File Launching**: Opens files using the default system application associated with the file extension.
- **Conditional Approved File Launch**: Only attempts to open the approved file if it exists and is non-empty.
- **Robust Error Handling**: Catches exceptions during launch and logs a warning instead of failing the test execution flow.

## Behavior
1. **report(received, approved)**: The main entry point for the reporter.
2. **Checks Approved File**: Verifies if the `approved` file is a non-empty file.
3. **Launches Approved File**: If the approved file exists and has content, it is opened using `TestUtils.displayFile(approved)`.
4. **Launches Received File**: The `received` file is always opened using `TestUtils.displayFile(received)`.
5. **Error Handling**: If any exception occurs during the process (e.g., file not found, no associated application), it is caught, logged as a warning, and the method returns `false`.
6. **Success**: Returns `true` if the operation completes without exception.


## Integration
- Implements the `ApprovalFailureReporter` interface.
