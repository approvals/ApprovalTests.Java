<a id="top"></a>

# Reporters

<!-- toc -->
## Contents

  * [Supported Diff Tools](#supported-diff-tools)
    * [Mac](#mac)
    * [Windows](#windows)
    * [Linux](#linux)
  * [Configuring a Reporter](#configuring-a-reporter)
    * [Via Options](#via-options)
    * [Class and Method level](#class-and-method-level)
      * [Single Reporter](#single-reporter)
    * [Package Level](#package-level)
  * [Combining Reporters](#combining-reporters)
  * [Custom Reporters](#custom-reporters)
  * [See Also](#see-also)<!-- endToc -->

## Supported Diff Tools

The DiffReporter class goes through a chain of possible reporters to find the first option installed on your system.
Currently the search goes in this order:

### Mac

<!-- snippet: mac_diff_reporters -->
<a id='snippet-mac_diff_reporters'></a>
```java
BeyondCompareMacReporter.INSTANCE,
DiffMergeReporter.INSTANCE,
KaleidoscopeDiffReporter.INSTANCE,
P4MergeReporter.INSTANCE,
KDiff3Reporter.INSTANCE,
TkDiffReporter.INSTANCE,
IntelliJReporter.INSTANCE,
VisualStudioCodeReporter.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/macosx/MacDiffReporter.java#L13-L22' title='Snippet source file'>snippet source</a> | <a href='#snippet-mac_diff_reporters' title='Start of snippet'>anchor</a></sup>
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
KDiff3Reporter.INSTANCE,
IntelliJReporter.INSTANCE,
VisualStudioCodeReporter.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/windows/WindowsDiffReporter.java#L13-L22' title='Snippet source file'>snippet source</a> | <a href='#snippet-windows_diff_reporters' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Linux

<!-- snippet: linux_diff_reporters -->
<a id='snippet-linux_diff_reporters'></a>
```java
DiffMergeReporter.INSTANCE,
MeldMergeReporter.INSTANCE,
IntelliJReporter.INSTANCE
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/reporters/linux/LinuxDiffReporter.java#L13-L17' title='Snippet source file'>snippet source</a> | <a href='#snippet-linux_diff_reporters' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Configuring a Reporter

You can configure which reporter to use in a few ways. ApprovalTests uses the method of principle of least surprise. Meaning the reporter uses is the one declared closest to the test. 

### Via Options

All the [verify](./reference/Verify.md) functions have an overload that takes an `Options` parameter. You can configure the reporter via the Options like such:

<!-- snippet: configure_reporter_with_options -->
<a id='snippet-configure_reporter_with_options'></a>
```java
Options options = new Options().withReporter(BeyondCompareReporter.INSTANCE);
Approvals.verify(objectUnderTest, options);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/ApprovalsTest.java#L22-L25' title='Snippet source file'>snippet source</a> | <a href='#snippet-configure_reporter_with_options' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Class and Method level

At both the class and method level you can use the @UseReporter attribute to set 1 or multiple reporters.

#### Single Reporter
<!-- snippet: use_reporter_single -->
<a id='snippet-use_reporter_single'></a>
```java
@UseReporter(DiffMergeReporter.class)
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/reporters/GenericDiffReporterTest.java#L20-L22' title='Snippet source file'>snippet source</a> | <a href='#snippet-use_reporter_single' title='Start of snippet'>anchor</a></sup>
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

You can find out more about [Package Level settings here](./reference/PackageSettings.md#top)

## Combining Reporters
You can combine reporters in two ways:
* `MultiReporter`
    This will launch every single reporter given to it
* `FirstWorkingReporter`
    This will launch the reporters in order until it finds the first one that works

You can make custom instances of these or extend them to report however you need to.

## Custom Reporters

If your favorite diff tool isn't supported out of the box, create your own custom extensions.
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

## See Also
* [How to capture .received. files from CI](how_to/CaptureFilesFromCI.md)

---

[Back to User Guide](README.md#top)
