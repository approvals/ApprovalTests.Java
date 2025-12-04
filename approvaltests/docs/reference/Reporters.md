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
BeyondCompareMacReporter.INSTANCE,
DiffMergeMacOsReporter.INSTANCE,
KaleidoscopeDiffReporter.INSTANCE,
P4MergeReporter.INSTANCE,
AraxisMergeReporter.INSTANCE,
KDiff3MacReporter.INSTANCE,
TkDiffReporter.INSTANCE,
IntelliJReporter.INSTANCE,
VisualStudioCodeReporter.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/macosx/MacDiffReporter.java#L13-L23' title='Snippet source file'>snippet source</a> | <a href='#snippet-mac_diff_reporters' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->


### Windows

<!-- snippet: windows_diff_reporters -->
<a id='snippet-windows_diff_reporters'></a>
```java
TortoiseDiffReporter.INSTANCE,
BeyondCompareReporter.INSTANCE,
WinMergeReporter.INSTANCE,
AraxisMergeReporter.INSTANCE,
CodeCompareReporter.INSTANCE,
KDiff3WindowsReporter.INSTANCE,
IntelliJReporter.INSTANCE,
VisualStudioCodeReporter.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/windows/WindowsDiffReporter.java#L13-L22' title='Snippet source file'>snippet source</a> | <a href='#snippet-windows_diff_reporters' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Linux

<!-- snippet: linux_diff_reporters -->
<a id='snippet-linux_diff_reporters'></a>
```java
DiffMergeLinuxReporter.INSTANCE,
MeldMergeReporter.INSTANCE,
IntelliJReporter.INSTANCE,
KDiff3LinuxReporter.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/linux/LinuxDiffReporter.java#L13-L18' title='Snippet source file'>snippet source</a> | <a href='#snippet-linux_diff_reporters' title='Start of snippet'>anchor</a></sup>
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

---

[Back to User Guide](../README.md#top)
