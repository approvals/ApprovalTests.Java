# FirstWorkingReporter toString Implementation

## Purpose
Provides a human-readable string for `FirstWorkingReporter` represents itself as a string for debugging, logging, and equality comparison.

## Behavior
Recursively calls each reporter to get their toString.

## Examples
```text
"FirstWorkingReporter(ReporterForTesting(True), ReporterForTesting(False))"
```

## Unit Tests
A single unit test that uses inline approvals, creates a reporter and verifies the toString.
