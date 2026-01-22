<a id="top"></a>

# How to Verify Multiple Files in a Single Test
<!-- toc -->
## Contents

  * [Introduction](#introduction)
  * [Sample Code](#sample-code)<!-- endToc -->

## Introduction
Sometimes your code produces multiple output files (e.g., CSV exports, generated reports, configuration files) and you want to verify all of them in a single test. Rather than writing individual assertions for each file, `Approvals.verifyEachFileInDirectory()` will verify every file in a directory against its corresponding approved file, and report all mismatches at once.

This is particularly useful for integration tests where a process generates multiple files and you want comprehensive feedback about all failures, rather than stopping at the first one.

## Sample Code

<!-- snippet: verify_multiple_files -->
<a id='snippet-verify_multiple_files'></a>
```java
Approvals.verifyEachFileInDirectory(directory);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/DirectoryOutputTest.java#L18-L20' title='Snippet source file'>snippet source</a> | <a href='#snippet-verify_multiple_files' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

You can also filter which files to verify using a `FileFilter` or `FilenameFilter`:

snippet: verify_multiple_files_with_filter

**Note:** If any files do not match their approved versions, the test will report all mismatched files together rather than failing on the first mismatch.
