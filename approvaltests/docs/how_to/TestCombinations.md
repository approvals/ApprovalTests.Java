<a id="top"></a>

# Testing Combinations

<!-- toc -->
## Contents

  * [When to use Combinations](#when-to-use-combinations)
  * [Steps](#steps)
  * [The Basics](#the-basics)<!-- endToc -->

## When to use Combinations

You have a function that takes, for example, 3 parameters, and you want to test its behaviour with a bunch of different values for each of those parameters.

If you have only one parameter that you want to vary, check out [How to Test a Variety of Values for One Input](TestAVarietyOfValues.md#top).

## Steps

1. Copy this starter text, and adjust for the number of inputs that you have.

<!-- snippet: CombinationsStartingPoint -->
<a id='snippet-CombinationsStartingPoint'></a>
```java
String[] inputs1 = {"input1.value1", "input1.value2"};
String[] inputs2 = {"input2.value1", "input2.value2", "input2.value3"};
CombinationApprovals.verifyAllCombinations((a, b) -> "placeholder", inputs1, inputs2);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/combinations/CombinationTest.java#L69-L73' title='Snippet source file'>snippet source</a> | <a href='#snippet-CombinationsStartingPoint' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

2. Modify each input container for your chosen values.
3. Run it, and make sure that you have your inputs wired up correctly.

If they are wired up correctly, you will see a file that looks like this: it is the left hand side of the file that
matters at this point: all combinations of your own input values should be listed:

<!-- snippet: CombinationTest.templateCode.approved.txt -->
<a id='snippet-CombinationTest.templateCode.approved.txt'></a>
```txt
[input1.value1, input2.value1] => placeholder 
[input1.value1, input2.value2] => placeholder 
[input1.value1, input2.value3] => placeholder 
[input1.value2, input2.value1] => placeholder 
[input1.value2, input2.value2] => placeholder 
[input1.value2, input2.value3] => placeholder
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/combinations/CombinationTest.templateCode.approved.txt#L1-L6' title='Snippet source file'>snippet source</a> | <a href='#snippet-CombinationTest.templateCode.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

5. Implement the body of your lambda
6. Run it, and approve the output.

## The Basics

You can use `CombinationApprovals.verifyAllCombinations` to test the content of multiple containers.

This makes a kind of approval test matrix, automatically testing all combinations of a set of inputs. It's a powerful way to quickly get very good test coverage.

In this small example, all combinations of `{"hello", "world"}` and `{1, 2, 3}` are being used:

<!-- snippet: YouCanVerifyCombinationsOf2 -->
<a id='snippet-YouCanVerifyCombinationsOf2'></a>
```java
String[] strings = {"hello", "world"};
Integer[] numbers = {1, 2, 3};
CombinationApprovals.verifyAllCombinations((s, i) -> String.format("(%s,%s)", s, i), strings, numbers);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/combinations/CombinationTest.java#L79-L83' title='Snippet source file'>snippet source</a> | <a href='#snippet-YouCanVerifyCombinationsOf2' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

The format is carefully chosen to show both inputs and outputs, to make the test results easy to interpret. The output looks like this:

<!-- snippet: CombinationTest.testCombinationsOfTwo.approved.txt -->
<a id='snippet-CombinationTest.testCombinationsOfTwo.approved.txt'></a>
```txt
[hello, 1] => (hello,1) 
[hello, 2] => (hello,2) 
[hello, 3] => (hello,3) 
[world, 1] => (world,1) 
[world, 2] => (world,2) 
[world, 3] => (world,3)
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/combinations/CombinationTest.testCombinationsOfTwo.approved.txt#L1-L6' title='Snippet source file'>snippet source</a> | <a href='#snippet-CombinationTest.testCombinationsOfTwo.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

For 
advice on effective formatting, see [Tips for Designing Strings](https://github.com/approvals/ApprovalTests.cpp/blob/master/doc/explanations/TipsForDesigningStrings.md#top). As you write out larger volumes of data in your approval files, experience has shown that the choice of layout of text in approval files can make a big difference to maintainability of tests, when failures occur.

---

[Back to User Guide](/doc/README.md#top)
