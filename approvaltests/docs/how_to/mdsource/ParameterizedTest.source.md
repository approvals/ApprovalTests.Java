<a id="top"></a>

# How to use Approvals.verify in Parameterized Tests
toc

By default, ApprovalTests generates 1 file per test.
In case of `@ParameterizedTests` when using `Approvals.verify(object)`, this is not the desired behaviour.

Here is some sample code on how to make ApprovalTests create multiple files for one ParameterizedTest, 
where each file name also includes the parameter name.

snippet: parameterized_test

This will make it so that the approved file includes the parameters, for example: Samples.sampleParameterizedTest.`parameter1`.approved.txt 
and Samples.sampleParameterizedTest.`parameter2`.approved.txt

Note, you might also want to give Approvals.verifyAll() in combination with `@Test` a try.
