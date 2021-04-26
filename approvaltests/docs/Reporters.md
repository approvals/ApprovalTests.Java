<a id="top"></a>

# Reporters

<!-- toc -->
## Contents

  * [Supported Diff Tools](#supported-diff-tools)
    * [Mac](#mac)
    * [Windows](#windows)
    * [Linux](#linux)
  * [Configuring a Reporter](#configuring-a-reporter)
    * [Class and Method level](#class-and-method-level)
    * [Package Level](#package-level)
  * [Custom Reporters](#custom-reporters)<!-- endToc -->

## Supported Diff Tools

The DiffReporter class goes through a chain of possible reporters to find the first option installed on your system.
Currently the search goes in this order:

### Mac

<!-- snippet: mac_diff_reporters -->
<a id='snippet-mac_diff_reporters'></a>
```java
BeyondCompareMacReporter.INSTANCE, DiffMergeReporter.INSTANCE, KaleidoscopeDiffReporter.INSTANCE,
P4MergeReporter.INSTANCE, KDiff3Reporter.INSTANCE, TkDiffReporter.INSTANCE, IntelliJReporter.INSTANCE,
VisualStudioCodeReporter.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/macosx/MacDiffReporter.java#L12-L16' title='Snippet source file'>snippet source</a> | <a href='#snippet-mac_diff_reporters' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->


### Windows

<!-- snippet: windows_diff_reporters -->
<a id='snippet-windows_diff_reporters'></a>
```java
TortoiseDiffReporter.INSTANCE, BeyondCompareReporter.INSTANCE, WinMergeReporter.INSTANCE,
AraxisMergeReporter.INSTANCE, CodeCompareReporter.INSTANCE, KDiff3Reporter.INSTANCE,
IntelliJReporter.INSTANCE, VisualStudioCodeReporter.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/windows/WindowsDiffReporter.java#L12-L16' title='Snippet source file'>snippet source</a> | <a href='#snippet-windows_diff_reporters' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Linux

<!-- snippet: linux_diff_reporters -->
<a id='snippet-linux_diff_reporters'></a>
```java
DiffMergeReporter.INSTANCE, MeldMergeReporter.INSTANCE, IntelliJReporter.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/linux/LinuxDiffReporter.java#L12-L14' title='Snippet source file'>snippet source</a> | <a href='#snippet-linux_diff_reporters' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Configuring a Reporter

You can configure which reporter to use in a few ways. ApprovalTests uses the method of principle of least surprise. Meaning the reporter uses is the one declared closest to the test. 

### Class and Method level

At both the class and method level you can use the @UseReporter attribute to set 1 or multiple reporters.

<!-- snippet: use_reporter_single -->
<a id='snippet-use_reporter_single'></a>
```java
@UseReporter(DiffMergeReporter.class)
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/reporters/GenericDiffReporterTest.java#L21-L23' title='Snippet source file'>snippet source</a> | <a href='#snippet-use_reporter_single' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

<!-- snippet: use_reporter_multiple -->
<a id='snippet-use_reporter_multiple'></a>
```java
@UseReporter({DiffReporter.class, ClipboardReporter.class})
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/machine_specific_tests/SamplesTest.java#L9-L11' title='Snippet source file'>snippet source</a> | <a href='#snippet-use_reporter_multiple' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

*Note*: If you use multiple reporters ALL of them will report. Not just the first one.

### Package Level

You can also assign a default for an entire package (and all sub-packages) by creating a PackageSettings class. Here is an example  


<!-- snippet: use_reporter_package_settings -->
<a id='snippet-use_reporter_package_settings'></a>
```java
public class PackageSettings
{
  public static TkDiffReporter   UseReporter         = TkDiffReporter.INSTANCE;
  public static CountingReporter FrontloadedReporter = new CountingReporter();
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/packagesettings/PackageSettings.java#L5-L11' title='Snippet source file'>snippet source</a> | <a href='#snippet-use_reporter_package_settings' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Custom Reporters

If your favorite diff tool isn't supported out of the box, create your own custom extentions.
Here's an example:

<!-- snippet: custom_reporter -->
<a id='snippet-custom_reporter'></a>
```java
public class CustomReporter extends GenericDiffReporter
{
  // optional singleton, but improves performance
  public static final CustomReporter INSTANCE = new CustomReporter();
  public CustomReporter()
  {
    super("/fullpath/to/diffProgram.exe");
  }
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/reporters/CustomReporter.java#L3-L13' title='Snippet source file'>snippet source</a> | <a href='#snippet-custom_reporter' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->


---

[Back to User Guide](README.md#top)
