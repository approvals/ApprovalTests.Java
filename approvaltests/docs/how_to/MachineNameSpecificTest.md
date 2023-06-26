<a id="top"></a>

# How to run tests only on specific machines

<!-- toc -->
## Contents

  * [Introduction](#introduction)
  * [Using NamedEnvironment](#using-namedenvironment)<!-- endToc -->

## Introduction
In some cases, you may want tests to run only on specific machines. 
This can be achieved by using `NamedEnvironment` at the beginning of your tests.


## Using NamedEnvironment
You can use the `NamedEnvironment` class in combination with `isCurrentEnvironmentValidFor` to achieve this.


Here is an example that uses the NamedEnvironment class to run tests only on a specific machine.
It checks if the current machine's name matches the specified name (e.g., ".lars-mbp-14"), and if not, the test is skipped. 
The actual test code should be placed after this condition.

<!-- snippet: runOnlyOnSpecificMachines -->
<a id='snippet-runonlyonspecificmachines'></a>
```java
try (NamedEnvironment namedEnvironment = NamerFactory.asMachineNameSpecificTest())
{
  if (!namedEnvironment.isCurrentEnvironmentValidFor(".lars-mbp-14"))
  { return; }
  // the rest of your test...
```
<sup><a href='/approvaltests/src/test/java/org/approvaltests/reporters/intellij/IntelliJPathResolverTest.java#L49-L55' title='Snippet source file'>snippet source</a> | <a href='#snippet-runonlyonspecificmachines' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

**please note:** this will also append the machine name to the ApprovalTest so that each specific
machine gets its own `.approved` file.
