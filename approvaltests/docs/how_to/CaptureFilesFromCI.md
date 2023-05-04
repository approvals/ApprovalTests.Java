<a id="top"></a>

# How to capture .received. files from CI

<!-- toc -->
## Contents

  * [The problem](#the-problem)
  * [The solution](#the-solution)
  * [Compatibility](#compatibility)<!-- endToc -->

## Introduction
This documentation explains how to capture received files from a Continuous Integration (CI) system when tests pass on your local machine but fail on CI.


## Problem Description
One of the challenges developers face is when tests pass on their local machine but fail on the CI system. This is particularly common with image or binary files but can occur with any file type.

## Solution: Using FileCaptureReporter
ApprovalTests provides a reporter called `FileCaptureReporter` that can help address this issue. When a test fails, `FileCaptureReporter` adds the received file to your Git repository and pushes it. This allows you to capture feedback from a headless CI machine.



**NOTE**: Keep in mind that this process will create one commit per received file. If multiple tests fail, you will have multiple commits.

Here is an example:

<!-- snippet: file_capture_reporter_example -->
<a id='snippet-file_capture_reporter_example'></a>
```java
CustomPanel panel = new CustomPanel(true, 20);
AwtApprovals.verify(panel, new Options(new FileCaptureReporter()));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/awt/ApprovalsTest.java#L32-L35' title='Snippet source file'>snippet source</a> | <a href='#snippet-file_capture_reporter_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

The example above demonstrates how to use FileCaptureReporter with ApprovalTests to capture received files on test failure.


## Compatibility
`FileCaptureReporter` is currently compatible with GitHub Actions and any machine where Git is configured to allow committing and pushing from the command line. You can extend this compatibility as needed for your specific system.





