<!-- toc -->
## Contents

  * [Parts of a Test](#parts-of-a-test)
  * [Strings](#strings)
  * [Objects](#objects)
    * [Using JSON](#using-json)
  * [Arrays](#arrays)
  * [Swing / AWT](#swing--awt)
  * [Generate Combinations of Parameter Values](#generate-combinations-of-parameter-values)
  * [Approving The Result](#approving-the-result)
  * [Reporters](#reporters)
  * [Supported Diff Tools](#supported-diff-tools)<!-- endToc -->

# Getting Started
## Parts of a Test

All tests (unit and otherwise) contain 2 parts:

- Do
- Verify

ApprovalTests is a way to handle the second part: Verification. All calls will look about the same:

<!-- snippet: basic_verified_call -->
<a id='snippet-basic_verified_call'></a>
```java
Approvals.verify(objectToBeVerified);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/GettingStartedTest.java#L21-L23' title='Snippet source file'>snippet source</a> | <a href='#snippet-basic_verified_call' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Strings

Letʼs say you wanted to test if a string was being built correctly:

<!-- snippet: verifying_strings -->
<a id='snippet-verifying_strings'></a>
```java
@Test
public void testBuildString()
{
  /* Do */
  // create a string with "Approval" and append "Tests" to it
  String s = "Approval";
  s += " Tests";
  /* Verify */
  // Verify the resulting string
  Approvals.verify(s);
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/GettingStartedTest.java#L25-L37' title='Snippet source file'>snippet source</a> | <a href='#snippet-verifying_strings' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Will Produce the following File:    
`GettingStartedTest.testBuildString.approved.txt`

Containing

<!-- snippet: GettingStartedTest.testBuildString.approved.txt -->
<a id='snippet-GettingStartedTest.testBuildString.approved.txt'></a>
```txt
Approval Tests
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/GettingStartedTest.testBuildString.approved.txt#L1-L1' title='Snippet source file'>snippet source</a> | <a href='#snippet-GettingStartedTest.testBuildString.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

This is the 'actual result'.   
The 'expected' result is in the file SampleTest.testBuildString.**received**.txt   
[(This is described in detail below.)](#ApprovingTheResult)
When you see the results you want (“ApprovalTests”) as the result,
simply [Approve The Result](#ApprovingTheResult).

## Objects

Let's say that you wanted to test that a customized StringBuilder was creating text correctly:

<!-- snippet: verifying_objects -->
<a id='snippet-verifying_objects'></a>
```java
@Test
public void testObject()
{
  /* Do */
  // create an 100 x 200 rectangle with the top corner at (5, 10)
  Rectangle objectUnderTest = new Rectangle(5, 10, 100, 200);
  /* Verify */
  // Verify the rectangle is properly defined
  Approvals.verify(objectUnderTest.toString());
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/GettingStartedTest.java#L38-L49' title='Snippet source file'>snippet source</a> | <a href='#snippet-verifying_objects' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Will Produce the following File:    
`GettingStartedTest.testObject.approved.txt`

Containing

<!-- snippet: GettingStartedTest.testObject.approved.txt -->
<a id='snippet-GettingStartedTest.testObject.approved.txt'></a>
```txt
java.awt.Rectangle[x=5,y=10,width=100,height=200]
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/GettingStartedTest.testObject.approved.txt#L1-L1' title='Snippet source file'>snippet source</a> | <a href='#snippet-GettingStartedTest.testObject.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Using JSON
If the object does not have a toString() method defined, and you do not want to (or can not) create a custom one, you can use JSON to verify the contents of an object:

<!-- snippet: verifying_objects_with_json -->
<a id='snippet-verifying_objects_with_json'></a>
```java
JsonApprovals.verifyAsJson(objectUnderTest);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/GettingStartedTest.java#L58-L60' title='Snippet source file'>snippet source</a> | <a href='#snippet-verifying_objects_with_json' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Will Produce the following File:    
`GettingStartedTest.testObjectWithJson.approved.json`

Containing

<!-- snippet: GettingStartedTest.testObjectWithJson.approved.json -->
<a id='snippet-GettingStartedTest.testObjectWithJson.approved.json'></a>
```json
{
  "x": 5,
  "y": 10,
  "width": 100,
  "height": 200
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/GettingStartedTest.testObjectWithJson.approved.json#L1-L6' title='Snippet source file'>snippet source</a> | <a href='#snippet-GettingStartedTest.testObjectWithJson.approved.json' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

If you see “ApprovalTests” as the result, simply [Approve The Result](#ApprovingTheResult).
Itʼs important to note that you will need to create a useful instance of the toString()
Method for objects you want to use.

## Arrays
Letʼs say you wanted to test an array of Strings:

<!-- snippet: verifying_arrays -->
<a id='snippet-verifying_arrays'></a>
```java
@Test
public void testArray()
{
  /* Do */
  // create a String Array and set values in the indexes
  String[] s = new String[2];
  s[0] = "Approval";
  s[1] = "Tests";
  /* Verify */
  // Verify the array
  Approvals.verifyAll("Text", s);
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/GettingStartedTest.java#L62-L75' title='Snippet source file'>snippet source</a> | <a href='#snippet-verifying_arrays' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Will Produce the following File:    

<!-- snippet: GettingStartedTest.testArray.approved.txt -->
<a id='snippet-GettingStartedTest.testArray.approved.txt'></a>
```txt
Text[0] = Approval
Text[1] = Tests
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/GettingStartedTest.testArray.approved.txt#L1-L2' title='Snippet source file'>snippet source</a> | <a href='#snippet-GettingStartedTest.testArray.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Note the use of the label, "Text". This is used to make the generated output easier to read:    

Again, simply [Approve The Result](#ApprovingTheResult)

## Swing / AWT
Letʼs say you wanted to test that youʼve created a JPanel correctly.
(This works for anything that extends java.awt.Component : awt, swing, JFrame, Label, etc...)

<!-- snippet: verifying_gui -->
<a id='snippet-verifying_gui'></a>
```java
/* Do */
// create a TV Guide and select a show for 3pm
TvGuide tv = new TvGuide();
tv.selectTime("3pm");
/* Verify */
// Verify the TvGuide
AwtApprovals.verify(tv);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/GettingStartedTest.java#L85-L93' title='Snippet source file'>snippet source</a> | <a href='#snippet-verifying_gui' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Will Produce the following File:

![Expected  Image](../../../approvaltests-tests/src/test/java/org/approvaltests/GettingStartedTest.testTvGuide.Windows_10.approved.png)


__First__, I want to note that even though there is a UI and a select box for times,
Iʼm not “poking” it to select the time. Just because we are looking at the UI at the end,
doesnʼt mean I need to manipulate it directly. We are programmers, and are not limited by
the constraints of the UI. I simply expose a selectTime(String value) function.

__Second__, this will produce a .png file containing a screen shot of the JPanel as a
result. Simply [Approve The Result](#ApprovingTheResult) when itʼs ready.

__Third__, because these will render differently on different operating systems.
These test automatically include a Machine Specific setting [NamerFactory.asOsSpecificTest()](https://github.com/approvals/ApprovalTests.Java/blob/master/java/org/approvaltests/namer/NamerFactory.java) which adds the os type (e.g: Mac_OS_X) to the file name

## Generate Combinations of Parameter Values
To simplify getting more comprehensive sets of test cases, or expanding code coverage,
ApprovalTests can generate combinations of possible parameters for a given function.

To do this, create an array of possible values for each parameter passed to a function
(up to nine parameters). Call the CombinationApprovals.verifyAllCombinations() method
passing the method to be called as a lambda. An example follows:

```java
    Integer[] points = new Integer[]{4, 5, 10};
    String[] words = new String[]{"Bookkeeper", "applesauce"};
    CombinationApprovals.verifyAllCombinations(
        (i, s) -> s.substring(0, i),
        points,
        words);
```

SampleTest.testSubstring.received.txt  

        [4, Bookkeeper] => Book
        [4, applesauce] => appl
        [5, Bookkeeper] => Bookk
        [5, applesauce] => apple
        [10, Bookkeeper] => Bookkeeper
        [10, applesauce] => applesauce

Here we are writing a single test that tries all 6 ( 3 ints * 2 String) combinations of
inputs and the results those will produce.

This will generate potentially hundreds or thousands of possible combinations of values.
As before, the output will be displayed and, if the results are satisfactory, [Approve The Result](#ApprovingTheResult).

<a name='ApprovingTheResult'></a>
## Approving The Result
When you run a test using ApprovalTests, it will generate a file named
“YourTestClass.yourTestMethod.received.txt” (or .png, .html, etc.) and place it in the same
directory as your test.

For the test to pass, this file must match:

YourTestClass.youTestMethod.__approved__.txt

There are many ways to do this:

1. Rename the .received file
2. Run the "move" command that is displayed (also added to your clipboard) in the command line
3. Use "use whole file" on a diff reporter
4. Use the "approve" command with the approval plugin (available for Eclipse)

Itʼs doesnʼt matter how you do it.

__Note__: If the files match, then the received file will be deleted.<br>
__Note__: You must include the `.approved.` files in your source control.

## Reporters
If an approval fails, then a report will be called that will report the “.received” and
“.approved” files. There are many reporters, and you can create your own.

The simplest way to have your reporter called is to use the Annotation @UseReporter(Reporter.class)
You can annotate at either the method or class level.

Here are some common Reporters and uses

Reporter             | Description
--------             | -----------
ClipboardReporter    | Puts the "move" command to accept the "received" file as the "approved" file
DiffReporter         | Launches an instance of the specified reporter
FileLauncherReporter | Opens the .received file in the specified editor
ImageReporter        | Launches an instance of the specified image diff tool
ImageWebReporter     | Opens the files in a Web browser
JunitReporter        | Text only, displays the contents of the files as an AssertEquals failure
NotePadLauncher      | Opens the .received file in Notepad++ (MS Windows only)
QuietReporter        | Outputs the "move" command to the console (great for build systems)
TextWebReporter      | Opens the text files in a Web browser

## Supported Diff Tools
See [Supported Diff Tools](../Reporters.md#supported-diff-tools)

If your diff tool of choice is not supported, or is in a non-standard install folder, you can always use a [Custom Reporter](../Reporters.md#custom-reporters)

