<a id="top"></a>
# Inline Approvals Workflow

<!-- toc -->
## Contents

  * [General flow](#general-flow)
  * [Step 1](#step-1)
  * [Step 2 - Run and approve by diff](#step-2---run-and-approve-by-diff)
  * [Step 3 - The resulting test file](#step-3---the-resulting-test-file)
  * [See also:](#see-also)<!-- endToc -->


## General flow
```mermaid
flowchart
WriteTest["Write a test with an empty expectation"] --> Fail
Fail --> reporter["Open Reporter of original test source code vs. modified source code(Diff Tool)"]
reporter --> ApproveNewSource["Approve the new source code"]
ApproveNewSource --> Pass
style Fail fill:#f00
style Pass fill:#0f0
```

Inline approvals feel a lot like regular approvals except for the expected result is stored in the source in the test method, like a regular JUnit test.

## Step 1
Write the test with no expectation
<!-- snippet: inline_approvals_before -->
<a id='snippet-inline_approvals_before'></a>
```java
@Test
public void testInlineApprovals()
{
  var expected = """
      """;
  Approvals.verify(getGreeting(), new Options().inline(expected));
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/InlineApprovalsExampleTest.java#L22-L30' title='Snippet source file'>snippet source</a> | <a href='#snippet-inline_approvals_before' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Step 2 - Run and approve by diff
When you run this, a diff program will pop up.
But instead of showing just the approved and received, it will show you the original source code and the source code needed to approve the received result.
Here is an example:
![Diff Reporter of Source](../images/inline_diff.png)

You can approve this the same as normal: by moving the received from the left to the right.

## Step 3 - The resulting test file
The resulting test will have the approved text at the top of the test.
<!-- snippet: inline_approvals_after -->
<a id='snippet-inline_approvals_after'></a>
```java
@Test
public void testInlineApprovals()
{
  var expected = """
      Hello world!
      Welcome to inline approvals!
      """;
  Approvals.verify(getGreeting(), new Options().inline(expected));
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/InlineApprovalsExampleTest.java#L36-L46' title='Snippet source file'>snippet source</a> | <a href='#snippet-inline_approvals_after' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## See also:
* [How to use inline approvals](../how_to/InlineApprovals.md)
