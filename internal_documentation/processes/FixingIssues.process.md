# Process for Fixing Bugs

When fixing a bug reported in an issue or pull request:

If you don't have the URL of the issue or pull request, ask for it.

1.  **Understand the Bug**:
    *   Read the pull request via the github cli: `gh issue view <number> --json body` to understand the problem, expected behavior, and actual behavior.

2.  **Write a Failing Test**:
    *   Create a new test case or modify an existing one in the relevant test suite (e.g., `approvaltests-tests` or `approvaltests-util-tests`).
    *   The test should specifically target the scenario described in the bug report and fail with the current codebase, clearly demonstrating the bug (e.g., throwing the specific exception, producing incorrect output).
    *   **Inline Approvals**: If the expected output for the test is reasonably short (e.g., less than 10 lines), use [Inline Approvals](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/InlineApprovals.md) within the test method itself (`new Options().inline(expected)`). This keeps the expected result alongside the test logic. For longer outputs, use standard approval files.

3.  **Implement the Fix**:
    *   Modify the necessary source code file(s) to correct the bug.
    *   Focus on addressing the root cause identified by the failing test.

4.  **Verify the Fix**:
    *   Run the test suite (`./build_and_test.sh`).
    *   Confirm that the previously failing test now passes.
    *   Ensure **all** other tests continue to pass, verifying no regressions were introduced.

5.  **Commit the Changes**:
    *   Use the Arlo Commit Notation (`<Risk Symbol> B <Title>`). Typically, the risk symbol will be `-` (Tested) if you wrote a unit test.
    *   Reference the fixed issue number in the commit message body (e.g., `Fixes #<issue_number>`).
    *   Include any necessary co-authors (refer to `ArloCommitNotation.process.md`).
    *   Use the commit script: `.windsurf/scripts/commit.sh "<commit_message>"`