<a id="top"></a>

# How to use inline approvals

<!-- toc -->
## Contents

  * [What it is](#what-it-is)
  * [Why use inline](#why-use-inline)
  * [Solution: Use Options.inline()](#solution-use-optionsinline)
  * [Workflow](#workflow)
  * [Options](#options)
    * [Show Code](#show-code)
    * [Reporters](#reporters)<!-- endToc -->

## What it is
While the expected result for most approvals in an a separate `.approved.` file, inline approvals stores the result as a string within the test source.

## Why use inline

Inline approvals is well-suited to tests with small expected values, e.g. under 10 lines long. Inline approvals has the following benefits:
* Reduced number of test files
* Result and test are co-located
* Having the result visible with the code clarifies the intent of the test
* It resembles more traditional unit tests 

## Solution: Use Options.inline()
Any `verify()` call can use inline approvals so long as the output is text.
To enable inline approvals, pass the option argument:

<!-- snippet: inline_approvals -->
<a id='snippet-inline_approvals'></a>
```java
new Options().inline(expected);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/inline/InlineApprovalsTest.java#L30-L32' title='Snippet source file'>snippet source</a> | <a href='#snippet-inline_approvals' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Workflow
On failure, approval tests will generate a temporary file of what your source code should look like if you were to approve this result and show it in the diff tool of your choice.
By moving over the code you will approve the result - and change the source file.

For a more detailed explanation of the workflow see [Inline Approvals Workflow](../explanations/InlineApprovalsWorkflow.md)

## Options

### Show Code
`Options.inline()` has two parameters:
* The expected result
* showCode flag (default `true`)
If you would prefer just to see the difference between the approved and received without the surrounding Java code, set the `showCode` parameter to `false`.

### Reporters
When you call `.inline()` whatever the current reporter is will be used to display the current code file with the generated code file on failure.
If you set a reporter _after_ this, it will override the showCode option setting it to `false` and only report the differences between the `.approved.` and `.received.`.
