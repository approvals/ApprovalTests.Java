# Approval Logs

This documents the files that are written to `.approval_tests_temp/` and the mechanics of how they work.

## Domain Concept

The approval logs track test execution state to enable post-test cleanup and recovery operations. They are ephemeral artifacts cleared at the start of each test run.

## `.approval_tests_temp/` Directory

- Created automatically when tests run
- Contains a `.gitignore` with `*` to exclude all contents from version control
- Houses log files and downloaded utility scripts

## `.approved_files.log`

**Purpose**: Records all approved files accessed during the current test run.

**Format**: One approved file path per line.

**Lifecycle**:
- Cleared when the module is first imported
- Appended to during each `verify()` call

**Use Case**: Enables cleanup of orphaned approval files via `remove_abandoned_files.py` script.

## `.failed_comparison.log`

**Purpose**: Records all failed approval comparisons.

**Format**: Each line contains: `<received_file> -> <approved_file>`

**Lifecycle**:
- Cleared when the module is first imported
- Appended to when `verify()` detects a mismatch

**Use Case**: Enables batch approval of all failures via `approve_all.py` script.

## Downloaded Scripts

Scripts are lazily downloaded from [ApprovalTests.CommonScripts](https://github.com/approvals/ApprovalTests.CommonScripts) repository when their corresponding log is first cleared:

- `remove_abandoned_files.py` - Uses `.approved_files.log`
- `approve_all.py` - Uses `.failed_comparison.log`

Downloads are idempotent (skipped if script already exists) and silently fail on network errors.

### Disabling Script Downloads

Set `APPROVALTESTS_DISABLE_SCRIPT_DOWNLOADS=1` to prevent automatic downloads. Log files are still created.
