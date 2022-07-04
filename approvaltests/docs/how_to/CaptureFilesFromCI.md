<a id="top"></a>

# How to capture .received. files from CI

<!-- toc -->
## Contents

  * [The problem](#the-problem)
  * [The solution](#the-solution)
  * [Compatibility](#compatibility)<!-- endToc -->


## The problem
Your tests pass on your machine but fail on the CI system and you're not sure why. This is most common with image files with image/binary files but can occur with any file type.

## The solution
ApprovalTests has a reporter `FileCaptureReporter` that, on failure, will add the received file to your Git repo and push it. This allows you to capture feedback from a headless CI machine.

**NOTE**: This will make one commit per received file. So if you have multiple failing tests, you will have multiple commits.

Here is an example:

<!-- snippet: file_capture_reporter_example -->
<a id='snippet-file_capture_reporter_example'></a>
```java
final CustomPanel panel = new CustomPanel(true, 20);
AwtApprovals.verify(panel, new Options(new FileCaptureReporter()));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/awt/ApprovalsTest.java#L35-L38' title='Snippet source file'>snippet source</a> | <a href='#snippet-file_capture_reporter_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Compatibility
This is currently compatible with GitHub actions and any machine where Git is configured so that you can commit and push from the command line. It can be extended as needed for your system.
