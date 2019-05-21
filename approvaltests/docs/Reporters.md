<!--
This file was generate by MarkdownSnippets.
Source File: /approvaltests/docs/mdsource/Reporters.source.md
To change this file edit the source file and then re-run the generation using either the dotnet global tool (https://github.com/SimonCropp/MarkdownSnippets#markdownsnippetstool) or using the api (https://github.com/SimonCropp/MarkdownSnippets#running-as-a-unit-test).
-->
<a id="top"></a>

# Reporters



<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Contents**

- [Supported Diff Tools](#supported-diff-tools)
  - [Mac](#mac)
  - [Windows](#windows)
- [Configuring a Reporter](#configuring-a-reporter)
  - [Class and Method level](#class-and-method-level)
  - [Package Level](#package-level)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->


## Supported Diff Tools

The DiffReporter class goes through a chain of possible reporters to find the first option installed on your system.
Currently the search goes in this order:

### Mac

<!-- snippet: mac_diff_reporters -->
```java
BeyondCompareMacReporter.INSTANCE,
DiffMergeReporter.INSTANCE,
KaleidoscopeDiffReporter.INSTANCE,
P4MergeReporter.INSTANCE,
KDiff3Reporter.INSTANCE,
TkDiffReporter.INSTANCE,
VisualStudioCodeReporter.INSTANCE
```
<sup>[snippet source](/approvaltests/target/classes/org/approvaltests/reporters/macosx/MacDiffReporter.java#L11-L19)</sup>
```java
BeyondCompareMacReporter.INSTANCE,
DiffMergeReporter.INSTANCE,
KaleidoscopeDiffReporter.INSTANCE,
P4MergeReporter.INSTANCE,
KDiff3Reporter.INSTANCE,
TkDiffReporter.INSTANCE,
VisualStudioCodeReporter.INSTANCE
```
<sup>[snippet source](/approvaltests/src/main/java/org/approvaltests/reporters/macosx/MacDiffReporter.java#L11-L19)</sup>
<!-- endsnippet -->


### Windows

<!-- snippet: windows_diff_reporters -->
```java
TortoiseDiffReporter.INSTANCE,
BeyondCompareReporter.INSTANCE,
WinMergeReporter.INSTANCE,
AraxisMergeReporter.INSTANCE,
CodeCompareReporter.INSTANCE,
KDiff3Reporter.INSTANCE,
VisualStudioCodeReporter.INSTANCE
```
<sup>[snippet source](/approvaltests/target/classes/org/approvaltests/reporters/windows/WindowsDiffReporter.java#L11-L19)</sup>
```java
TortoiseDiffReporter.INSTANCE,
BeyondCompareReporter.INSTANCE,
WinMergeReporter.INSTANCE,
AraxisMergeReporter.INSTANCE,
CodeCompareReporter.INSTANCE,
KDiff3Reporter.INSTANCE,
VisualStudioCodeReporter.INSTANCE
```
<sup>[snippet source](/approvaltests/src/main/java/org/approvaltests/reporters/windows/WindowsDiffReporter.java#L11-L19)</sup>
<!-- endsnippet -->


## Configuring a Reporter

You can configure which reporter to use in a few ways. ApprovalTests uses the method of principle of least surprise. Meaning the reporter uses is the one declared closest to the test. 

### Class and Method level

At both the class and method level you can use the @UseReporter attribute to set 1 or multiple reporters.

<!-- snippet: use_reporter_single -->
```java
@UseReporter(DiffMergeReporter.class)
```
<sup>[snippet source](/approvaltests/src/test/java/org/approvaltests/reporters/tests/GenericDiffReporterTest.java#L27-L29)</sup>
<!-- endsnippet -->

<!-- snippet: use_reporter_multiple -->
```java
@UseReporter({DiffReporter.class, ClipboardReporter.class})
```
<sup>[snippet source](/approvaltests/src/test/java/machine_specific_tests/approvaltests/tests/Samples.java#L11-L13)</sup>
<!-- endsnippet -->

*Note*: If you use multiple reporters ALL of them will report. Not just the first one.

### Package Level

You can also assign a default for an entire package (and all sub-packages) by creating a PackageSettings class. Here is an example  


<!-- snippet: use_reporter_package_settings -->
```java
public class PackageSettings
{
  public static TkDiffReporter   UseReporter         = TkDiffReporter.INSTANCE;
  public static CountingReporter FrontloadedReporter = new CountingReporter();
}
```
<sup>[snippet source](/approvaltests/target/classes/org/approvaltests/packagesettings/tests/PackageSettings.java#L5-L11)</sup>
```java
public class PackageSettings
{
  public static TkDiffReporter   UseReporter         = TkDiffReporter.INSTANCE;
  public static CountingReporter FrontloadedReporter = new CountingReporter();
}
```
<sup>[snippet source](/approvaltests/src/main/java/org/approvaltests/packagesettings/tests/PackageSettings.java#L5-L11)</sup>
<!-- endsnippet -->

---

[Back to User Guide](README.md#top)
