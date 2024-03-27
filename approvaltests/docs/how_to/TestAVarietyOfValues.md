<a id="top"></a>

# How to Test a Variety of Values for One Input

<!-- toc -->
## Contents

  * [When to use Approvals::verifyAll()](#when-to-use-approvalsverifyall)
  * [Steps](#steps)
  * [Tables](#tables)<!-- endToc -->

## When to use Approvals::verifyAll()

When you want to test a lot of variations for a single input value.

If you have more than one parameter that you want to vary, check out [Testing Combinations](TestCombinations.md#top).

## Steps

1. Copy this starter text.

<!-- snippet: VerifyAllStartingPoint -->
<a id='snippet-VerifyAllStartingPoint'></a>
```java
String[] inputs = {"input.value1", "input.value2"};
Approvals.verifyAll("TITLE", inputs, s -> "placeholder " + s);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/ApprovalsTest.java#L46-L49' title='Snippet source file'>snippet source</a> | <a href='#snippet-VerifyAllStartingPoint' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

2. Modify the input container for your chosen values.
3. Run it, and make sure that you have your inputs wired up correctly.

If they are wired up correctly, you will see a file that looks like this: the lambda is responsible for both the execution and formatting of the result.

<!-- snippet: ApprovalsTest.verifyAllTemplate.approved.txt -->
<a id='snippet-ApprovalsTest.verifyAllTemplate.approved.txt'></a>
```txt
TITLE


placeholder input.value1
placeholder input.value2
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/ApprovalsTest.verifyAllTemplate.approved.txt#L1-L5' title='Snippet source file'>snippet source</a> | <a href='#snippet-ApprovalsTest.verifyAllTemplate.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

4. Replace the "placeholder" with a call to the functionality that you want to test.
5. Change the TITLE to something meaningful
6. Run it, and approve the output.


## Tables

Another way to test a variety of inputs is to use a `MarkdownTable`.
Here's an example:

<!-- snippet: markdown_table_example -->
<a id='snippet-markdown_table_example'></a>
```java
String[] inputs = {"verify json", "verify all", "verify parameters", "verify as json"};
VerifiableMarkdownTable table = VerifiableMarkdownTable.withHeaders("Input", "Camel Case", "Snake Case",
    "Kebab Case");
table.addRowsForInputs(inputs, this::toCamelCase, this::toSnakeCase, this::toKebabCase);
Approvals.verify(table);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/TableTest.java#L28-L34' title='Snippet source file'>snippet source</a> | <a href='#snippet-markdown_table_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

which will produce:

<!-- include: TableTest.differentCases.approved.md -->
|       Input       |    Camel Case    |    Snake Case     |    Kebab Case     |
|-------------------|------------------|-------------------|-------------------|
|    verify json    |    verifyJson    |    verify_json    |    verify-json    |
|    verify all     |    verifyAll     |    verify_all     |    verify-all     |
| verify parameters | verifyParameters | verify_parameters | verify-parameters |
|  verify as json   |   verifyAsJson   |  verify_as_json   |  verify-as-json   |
<!-- endInclude -->


---

[Back to User Guide](/doc/README.md#top)
