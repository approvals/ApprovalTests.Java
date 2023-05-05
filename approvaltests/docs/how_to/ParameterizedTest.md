<a id="top"></a>

# How to use Approvals.verify in Parameterized Tests
<!-- toc -->
## Contents

  * [Introduction](#introduction)
  * [Sample Code](#sample-code)<!-- endToc -->

## Introduction
By default, ApprovalTests generates one file per test. However, for `@ParameterizedTests`, this may not be the desired behavior when using `Approvals.verify(object)`. 
The following section demonstrates how to generate multiple files for a single ParameterizedTest, where each file name includes the parameter name.


## Sample Code

<!-- snippet: parameterized_test -->
<a id='snippet-parameterized_test'></a>
```java
@ParameterizedTest
@ValueSource(strings = {"parameter1", "parameter2"})
void sampleParameterizedTest(String parameter)
{
  // your code goes here
  Object output = parameter;
  Approvals.verify(output, Approvals.NAMES.withParameters(parameter));
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/SamplesTest.java#L38-L47' title='Snippet source file'>snippet source</a> | <a href='#snippet-parameterized_test' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

This code sample ensures that the approved file includes the parameters. For example:  
For example: 
1. SamplesTest.sampleParameterizedTest.`parameter1`.approved.txt 
1. SamplesTest.sampleParameterizedTest.`parameter2`.approved.txt

**Note:** As an alternative, consider using `Approvals.verifyAll()` in combination with `@Test`.
