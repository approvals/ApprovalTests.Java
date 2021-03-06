<a id="top"></a>

# How to use Approvals.verify in Parameterized Tests
<!-- toc -->
<!-- endToc -->

By default, ApprovalTests generates 1 file per test.
In case of `@ParameterizedTests` when using `Approvals.verify(object)`, this is not the desired behaviour.

Here is some sample code on how to make ApprovalTests create multiple files for one ParameterizedTest, 
where each file name also includes the parameter name.

<!-- snippet: parameterized_test -->
<a id='snippet-parameterized_test'></a>
```java
@ParameterizedTest
@ValueSource(strings = {"parameter1", "parameter2"})
void sampleParameterizedTest(String parameter)
{
  try (NamedEnvironment en = NamerFactory.withParameters(parameter))
  {
    // your code goes here
    Object output = parameter;
    Approvals.verify(output);
  }
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/Samples.java#L38-L50' title='Snippet source file'>snippet source</a> | <a href='#snippet-parameterized_test' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

This will make it so that the approved file includes the parameters, for example: Samples.sampleParameterizedTest.`parameter1`.approved.txt 
and Samples.sampleParameterizedTest.`parameter2`.approved.txt

Note, you might also want to give Approvals.verifyAll() in combination with `@Test` a try.
