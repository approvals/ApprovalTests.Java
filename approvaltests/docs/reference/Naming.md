<a id="top"></a>

# Naming

<!-- toc -->
## Contents

  * [Additional Naming Parameters](#additional-naming-parameters)
  * [How to do this](#how-to-do-this)
    * [The old way](#the-old-way)
    * [The new way](#the-new-way)
      * [Adding to an existing scenario](#adding-to-an-existing-scenario)
  * [Parameterized Tests](#parameterized-tests)
  * [Only running on a specific machine](#only-running-on-a-specific-machine)
  * [Capturing approvals from CI](#capturing-approvals-from-ci)<!-- endToc -->

## Additional Naming Parameters

By default an approval file will take the form of:  
`classname.methodname.approved.txt`  

There are a few cases where this is not sufficient:
* You need more than one approval in a test method
* You are using parameterized tests
* You need different approvals per computer/OS/platform

The way this is handled is through the naming factories. This will give you approval tests with the following:  
`classname.methodname.additionalInformation.approved.txt`

## How to do this

### The old way
There is a global namer factory that can be accessed with a try/catch. While this way works, it is not thread-safe
and can cause trouble when running tests in parallel. Here is an example of how to do it:
<!-- snippet: namer_factory_example -->
<a id='snippet-namer_factory_example'></a>
```java
try (NamedEnvironment namer = NamerFactory.withParameters("title", "chapter"))
{
  Approvals.verify("data");
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/NamerFactoryForOptionsTest.java#L28-L33' title='Snippet source file'>snippet source</a> | <a href='#snippet-namer_factory_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

This will result in a file named:
`ClassName.methodName.firstString.secondString.approved.txt`

### The new way
With the addition of `Options` you can now set additional naming information in a thread-safe way. Here is an example of how to do that:

<!-- snippet: additional_information_example -->
<a id='snippet-additional_information_example'></a>
```java
Approvals.verify("data", Approvals.NAMES.withParameters("title", "chapter"));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/NamerFactoryForOptionsTest.java#L39-L41' title='Snippet source file'>snippet source</a> | <a href='#snippet-additional_information_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

This will result in a file named:
`ClassName.methodName.firstString.secondString.approved.txt`

#### Adding to an existing scenario
If there is already an `Options` created, you can add to it using the `.and` keyword. Here is an example:

<!-- snippet: options_and -->
<a id='snippet-options_and'></a>
```java
Options options = Approvals.NAMES.asOsSpecificTest().and(Approvals.NAMES::asMachineNameSpecificTest);
ApprovalNamer namer = options.forFile().getNamer();
String name = namer.getApprovedFile(".txt").getName();
assertEquals("NamerFactoryForOptionsTest.testMachineSpecificTest.Mac_OS_X.lars-mbp-14.approved.txt", name);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/namer/NamerFactoryForOptionsTest.java#L49-L54' title='Snippet source file'>snippet source</a> | <a href='#snippet-options_and' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

This will result in a file named: 
`ClassName.methodName.OSName.machineName.approved.txt`

## Parameterized Tests
If you are using parameterized tests you will need to use additional names. See [How to use Approvals.verify in Parameterized Tests](../how_to/ParameterizedTest.md).

## Only running on a specific machine

Sometimes you have tests that cannot run on all of your machines. See [How to run tests only on specific machines](../how_to/MachineNameSpecificTest.md) 

## Capturing approvals from CI
If you are using special names per OS/platform your CI machines might not have a corresponding dev box. To handle this see [How to capture .received. files from CI](../how_to/CaptureFilesFromCI.md)
