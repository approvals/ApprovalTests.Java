<a id="top"></a>

# How to run tests only on specific machines

<!-- toc -->
## Contents

  * [Introduction](#introduction)
  * [Separate approved files per machine](#separate-approved-files-per-machine)<!-- endToc -->

## Introduction
In some cases, you may want tests to run only on specific machines.
Use your test framework's assumptions to skip the test on every other machine:

<!-- snippet: run_only_on_specific_machines -->
<a id='snippet-run_only_on_specific_machines'></a>
```java
assumeTrue(SystemUtils.getComputerName().equals("RSI-FQ61HDHJ0K"));
// the rest of your test...
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/UpdateFromNamerFactoryToOptionsTest.java#L62-L65' title='Snippet source file'>snippet source</a> | <a href='#snippet-run_only_on_specific_machines' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Separate approved files per machine
To give each machine its own `.approved` file, add the machine name to the file name:

<!-- snippet: namer_factory_machine_specific_after -->
<a id='snippet-namer_factory_machine_specific_after'></a>
```java
Options options = Approvals.NAMES.asMachineNameSpecificTest();
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/UpdateFromNamerFactoryToOptionsTest.java#L39-L41' title='Snippet source file'>snippet source</a> | <a href='#snippet-namer_factory_machine_specific_after' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Then call `Approvals.verify(result, options)`.
