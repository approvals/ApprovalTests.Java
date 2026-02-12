<a id="top"></a>

# Reporters

<!-- toc -->
## Contents

  * [Supported Diff Tools](#supported-diff-tools)
    * [Mac](#mac)
    * [Windows](#windows)
    * [Linux](#linux)
  * [Combining Reporters](#combining-reporters)
  * [See Also](#see-also)<!-- endToc -->

## Supported Diff Tools

The DiffReporter class goes through a chain of possible reporters to find the first option installed on your system.
Currently the search goes in this order:

### Mac

<!-- snippet: mac_diff_reporters -->
<a id='snippet-mac_diff_reporters'></a>
```java
ReportWithDiffMergeMac.INSTANCE,
ReportWithFileMergeMac.INSTANCE,
ReportWithBeyondCompareMac.INSTANCE,
ReportWithKaleidoscopeMac.INSTANCE,
ReportWithKaleidoscope3Mac.INSTANCE,
ReportWithKdiff3Mac.INSTANCE,
ReportWithP4mergeMac.INSTANCE,
ReportWithTkDiffMac.INSTANCE,
ReportWithVisualStudioCodeMac.INSTANCE,
ReportWithAraxisMergeMac.INSTANCE,
ReportWithDiffCommandLineMac.INSTANCE,
ReportWithSublimeMergeMac.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/macosx/ReportWithDiffToolOnMac.java#L14-L27' title='Snippet source file'>snippet source</a> | <a href='#snippet-mac_diff_reporters' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->


### Windows

<!-- snippet: windows_diff_reporters -->
<a id='snippet-windows_diff_reporters'></a>
```java
ReportWithBeyondCompare3Windows.INSTANCE,
ReportWithBeyondCompare4Windows.INSTANCE,
ReportWithBeyondCompare5Windows.INSTANCE,
ReportWithTortoiseImageDiffWindows.INSTANCE,
ReportWithTortoiseTextDiffWindows.INSTANCE,
ReportWithTortoiseGitImageDiffWindows.INSTANCE,
ReportWithTortoiseGitTextDiffWindows.INSTANCE,
ReportWithWinMergeReporterWindows.INSTANCE,
ReportWithAraxisMergeWindows.INSTANCE,
ReportWithCodeCompareWindows.INSTANCE,
ReportWithKdiff3Windows.INSTANCE,
ReportWithVisualStudioCodeWindows.INSTANCE,
ReportWithSublimeMergeWindows.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/windows/ReportWithDiffToolOnWindows.java#L14-L28' title='Snippet source file'>snippet source</a> | <a href='#snippet-windows_diff_reporters' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Linux

<!-- snippet: linux_diff_reporters -->
<a id='snippet-linux_diff_reporters'></a>
```java
ReportWithDiffMergeLinux.INSTANCE,
ReportWithMeldMergeLinux.INSTANCE,
ReportWithKdiff3Linux.INSTANCE,
ReportWithDiffCommandLineLinux.INSTANCE,
ReportWithSublimeMergeLinux.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/linux/ReportWithDiffToolOnLinux.java#L14-L20' title='Snippet source file'>snippet source</a> | <a href='#snippet-linux_diff_reporters' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Combining Reporters
You can combine reporters in two ways:
* `MultiReporter`
    This will launch every single reporter given to it
* `FirstWorkingReporter`
    This will launch the reporters in order until it finds the first one that works

You can make custom instances of these or extend them to report however you need to.

## See Also
* [How to Configure Reporters](../how_to/ConfigureReporters.md)
* [How to Create Your Own Custom Reporter](../how_to/CreateCustomReporter.md)
* [How to capture .received. files from CI](../how_to/CaptureFilesFromCI.md)
* [IntelliJReporter](IntelliJReporter.md)
* [FrontLoadedReporter](FrontLoadedReporter.md)
* [Why we removed the environment aware reporter in 2023](../explanations/RemovingEnvironmentAwareReporter.md)
---

[Back to User Guide](../README.md#top)
