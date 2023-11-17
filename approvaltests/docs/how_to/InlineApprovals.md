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
The expected result for most approvals in an a separate `.approved.` file
Inline approvals stores the result as a string within the test source.

## Why use inline
When your result is under ten lines inline approvals has the following benefits:
* Reduced number of test files
* Result and test are co-located
* Having the result visible with the code clarifies the intent of the test

## Solution: Use Options.inline()
Any `verify()` call can use inline approvals so long as the output is text.
To do this simply add the option:

<!-- snippet: inline_approvals -->
<a id='snippet-inline_approvals'></a>
```java
new Options().inline(expected);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/InlineApprovalsTest.java#L23-L25' title='Snippet source file'>snippet source</a> | <a href='#snippet-inline_approvals' title='Start of snippet'>anchor</a></sup>
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
If you would prefer just to see the difference between the approved and received without the surrounding Java code, set showCode to `false`.
### Reporters
When you call `.inline()` whatever the current reporter is will be used to display the current code file with the generated code file on failure.
If you set a reporter _after_ this, it will override the showCode option setting it to `false` and only report the differences between the `.approved.` and `.received.`.
