<a id="top"></a>

# How to update from NamerFactory to Options

<!-- toc -->
## Contents

  * [Why](#why)
  * [Parameters](#parameters)
  * [OS specific tests](#os-specific-tests)
  * [Machine name specific tests](#machine-name-specific-tests)
  * [Custom labels](#custom-labels)
  * [Multiple files in one test](#multiple-files-in-one-test)
  * [Running only on specific machines](#running-only-on-specific-machines)<!-- endToc -->

## Why

`NamerFactory` stores additional naming information in a global, thread-local value inside a `try` block.
This is not safe when tests run in parallel, and it is easy to forget to close.
`Options` carry the same information with each `verify` call, so nothing is shared between tests.

Every `NamerFactory` method has an `Options` replacement on `Approvals.NAMES`.
Pass the resulting `Options` to `verify` and remove the `try` block.
The approved file names stay the same, so you do not need to re-approve anything.

## Parameters

Before:

<!-- snippet: namer_factory_with_parameters_before -->
<a id='snippet-namer_factory_with_parameters_before'></a>
```java
try (NamedEnvironment en = NamerFactory.withParameters("title", "chapter"))
{
  before = getApprovedFileName(new Options());
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/UpdateFromNamerFactoryToOptionsTest.java#L17-L22' title='Snippet source file'>snippet source</a> | <a href='#snippet-namer_factory_with_parameters_before' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

After:

<!-- snippet: namer_factory_with_parameters_after -->
<a id='snippet-namer_factory_with_parameters_after'></a>
```java
Options options = Approvals.NAMES.withParameters("title", "chapter");
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/UpdateFromNamerFactoryToOptionsTest.java#L23-L25' title='Snippet source file'>snippet source</a> | <a href='#snippet-namer_factory_with_parameters_after' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Then call `Approvals.verify(result, options)`.

## OS specific tests

Before:

<!-- snippet: namer_factory_os_specific_before -->
<a id='snippet-namer_factory_os_specific_before'></a>
```java
try (NamedEnvironment en = NamerFactory.asOsSpecificTest())
{
  before = getApprovedFileName(new Options());
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/UpdateFromNamerFactoryToOptionsTest.java#L33-L38' title='Snippet source file'>snippet source</a> | <a href='#snippet-namer_factory_os_specific_before' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

After:

<!-- snippet: namer_factory_os_specific_after -->
<a id='snippet-namer_factory_os_specific_after'></a>
```java
Options options = Approvals.NAMES.asOsSpecificTest();
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/UpdateFromNamerFactoryToOptionsTest.java#L39-L41' title='Snippet source file'>snippet source</a> | <a href='#snippet-namer_factory_os_specific_after' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Machine name specific tests

Replace `NamerFactory.asMachineNameSpecificTest()` with:

<!-- snippet: namer_factory_machine_specific_after -->
<a id='snippet-namer_factory_machine_specific_after'></a>
```java
Options options = Approvals.NAMES.asMachineNameSpecificTest();
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/UpdateFromNamerFactoryToOptionsTest.java#L53-L55' title='Snippet source file'>snippet source</a> | <a href='#snippet-namer_factory_machine_specific_after' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Custom labels

Replace `NamerFactory.asMachineSpecificTest("jdk17")` with:

<!-- snippet: namer_factory_custom_label_after -->
<a id='snippet-namer_factory_custom_label_after'></a>
```java
Options options = new Options().forFile().withAdditionalInformation("jdk17");
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/UpdateFromNamerFactoryToOptionsTest.java#L67-L69' title='Snippet source file'>snippet source</a> | <a href='#snippet-namer_factory_custom_label_after' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Multiple files in one test

Replace `NamerFactory.useMultipleFiles()` with a counter that gives new `Options` for each verify:

<!-- snippet: multiple_files_via_options -->
<a id='snippet-multiple_files_via_options'></a>
```java
FileCounter labeller = Approvals.NAMES.useMultipleFiles();
Approvals.verify("one", labeller.next());
Approvals.verify("two", labeller.next());
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/NamerFactoryTest.java#L24-L28' title='Snippet source file'>snippet source</a> | <a href='#snippet-multiple_files_via_options' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Running only on specific machines

`NamedEnvironment.isCurrentEnvironmentValidFor(...)` was used to skip a test on other machines.
Use your test framework's assumptions instead:

<!-- snippet: run_only_on_specific_machines -->
<a id='snippet-run_only_on_specific_machines'></a>
```java
assumeTrue(SystemUtils.getComputerName().equals("RSI-FQ61HDHJ0K"));
// the rest of your test...
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/UpdateFromNamerFactoryToOptionsTest.java#L76-L79' title='Snippet source file'>snippet source</a> | <a href='#snippet-run_only_on_specific_machines' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

---

[Back to User Guide](/approvaltests/docs/README.md#top)
