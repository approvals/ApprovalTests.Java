<!--
This file was generate by MarkdownSnippets.
Source File: /approvaltests/docs/mdsource/Features.source.md
To change this file edit the source file and then re-run the generation using either the dotnet global tool (https://github.com/SimonCropp/MarkdownSnippets#markdownsnippetstool) or using the api (https://github.com/SimonCropp/MarkdownSnippets#running-as-a-unit-test).
-->
<a id="top"></a>

# Features



<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Contents**

- [Test Commit Revert](#test-commit-revert)
  - [On success](#on-success)
  - [On failure](#on-failure)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Test Commit Revert

This is a convenience class to follow the practice 'Test Commit/Revert' from Kent Beck. Simply adding
<!-- snippet: test_commit_revert -->
```java
public class TestCommitRevertSample extends TestCommitRevert
```
<sup>[snippet source](/approvaltests/src/test/java/org/approvaltests/legacycode/tests/TestCommitRevertSample.java#L8-L10)</sup>
<!-- endsnippet -->
to you test class will invoke 1 of 2 options

### On success
A dialog will appear asking for a commit message. Once given it will commit all files with that message
![prompt](images/commit_dialog.png)

### On failure
It will revert all changes

Note: this currently only works with git


---

[Back to User Guide](README.md#top)
