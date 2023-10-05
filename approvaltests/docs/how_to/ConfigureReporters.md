<a id="top"></a>

# How to Configure a Reporter

<!-- toc -->
## Contents

  * [Introduction](#introduction)
  * [Problem Description](#problem-description)
  * [Solution: Using FileCaptureReporter](#solution-using-filecapturereporter)
  * [Compatibility](#compatibility)<!-- endToc -->

## Problem Statement
Reporters are the part of Approval Tests that launch diff tools when things do not match.
The can also do [other things](../explanations/BenefitsOfDifferentReporters.md).
On this page we show you three different ways to configure which reporters are used.

## Principle of Least Surprise

Because there are a few different ways to configure a reporter in Approval Tests,
if you are using multiple methods to configure a reporter (this is very common)
you may wonder;
> Which configuration will be used?

ApprovalTests uses the method of `Principle of Least Surprise`.
This means the reporter used is the one declared closest to the test. 
Below are three different options in order, starting with the least surprising.

### Method 1: Via Options

All the [verify](./reference/Verify.md) functions have an overload that takes an `Options` parameter. You can configure the reporter via the Options like such:

<!-- snippet: configure_reporter_with_options -->
<a id='snippet-configure_reporter_with_options'></a>
```java
Options options = new Options().withReporter(BeyondCompareReporter.INSTANCE);
Approvals.verify(objectUnderTest, options);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/ApprovalsTest.java#L24-L27' title='Snippet source file'>snippet source</a> | <a href='#snippet-configure_reporter_with_options' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Method 2: Class and Method Level Annotations

At both the class and method level you can use the @UseReporter attribute to set 1 or multiple reporters.

#### Single Reporter
<!-- snippet: use_reporter_single -->
<a id='snippet-use_reporter_single'></a>
```java
@UseReporter(DiffMergeReporter.class)
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/reporters/GenericDiffReporterTest.java#L20-L22' title='Snippet source file'>snippet source</a> | <a href='#snippet-use_reporter_single' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
#### Multiple Reporters

<!-- snippet: use_reporter_multiple -->
<a id='snippet-use_reporter_multiple'></a>
```java
@UseReporter({DiffReporter.class, ClipboardReporter.class})
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/machine_specific_tests/SamplesTest.java#L9-L11' title='Snippet source file'>snippet source</a> | <a href='#snippet-use_reporter_multiple' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

*Note*: If you use multiple reporters ALL of them will report. Not just the first one.

### Method 3: Package Level

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

You can find out more about [Package Level settings here](../reference/PackageSettings.md#top)

See Also: [Reporters](../reference/Reporters.md)