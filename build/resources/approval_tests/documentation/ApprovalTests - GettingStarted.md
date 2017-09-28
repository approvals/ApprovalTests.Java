Getting Started
===============
Installation
---
All you need to do to use ApprovalTests is simply include the ApprovalTests.jar in your class path. Then use it with your favorite Testing Framework.

Parts of a Test
---
All tests (unit and otherwise) contain 2 parts:

- Do
- Verify

ApprovalTests is a way to handle the second part: Verification. All calls will look about the same:

    Approvals.verify(objectToBeVerified)

Strings
---
Letʼs say you wanted to test if a string was being built correctly:
```java
    public void testBuildString() {
        ////////// Do  ///////////
        // create a string with "Approval" and append "Tests" to it
        String s = "Approval";
        s += "Tests";

        ////////// Verify  ///////////
        // Verify the resulting string
        Approvals.verify(s);
    }
```

Will Produce the following File:    
SampleTest.testBuildString.received.txt  

        ApprovalTests

This is the 'actual result'.   
The 'expected' result is in the file SampleTest.testBuildString.**received**.txt   
[(This is described in detail below.)](#ApprovingTheResult)
When you see the results you want (“ApprovalTests”) as the result, simply [Approve The Result](#ApprovingTheResult).

Objects
---
Let's say that you wanted to test that a customized StringBuilder was creating text correctly:
```java
    public void testStringBuilder() {
        ////////// Do  ///////////
        // create my String Builder and append some strings
        MyStringBuilder sb = new MyStringBuilder();
        sb.append("Approval");
        sb.append("Tests");

        ////////// Verify  ///////////
        // Verify the object
        Approvals.verify(sb.toString());
    }
```

If you see “ApprovalTests” as the result, simply [Approve The Result](#ApprovingTheResult). Itʼs important to note that you will need to create a useful instance of the toString() Method for objects you want to use.

Arrays
---
Letʼs say you wanted to test an array of Strings:
```java
    public void testArray() {
        ////////// Do  ///////////
        // create a String Array and set values in the indexes
        String[] s = new String[2];
        s[0] = "Approval"
        s[1] = "Tests"

        ////////// Verify  ///////////
        // Verify the array
        Approvals.verifyAll("Text", x);
    }
```

Note the use of the label, "Text". This is used to make the generated output easier to read:    
SampleTest.testArray.received.txt  

    Text[0] = Approval
    Text[1] = Tests

Again, simply [Approve The Result](#ApprovingTheResult)

Swing / AWT
---
Letʼs say you wanted to test that youʼve created a JPanel correctly. (This works for anything that extends java.awt.Component : awt, swing, JFrame, Label, etc...)

```java
    public void testTvGuide() {
        ////////// Do  ///////////
        // create a TV Guide and select a show for 3pm
        TvGuide tv = new TvGuide();
        tv.selectTime("3pm");

        ////////// Verify  ///////////
        // Verify the TvGuide
        Approvals.verify(tv);
    }
```

SampleTest.testTvGuide.Mac_OS_X.approved.png  
![Expected  Image](ApprovalsTest.testTvGuide.Mac_OS_X.approved.png)


__First__, I want to note that even though there is a UI and a select box for times, Iʼm not “poking” it to select the time. Just because we are looking at the UI at the end, doesnʼt mean I need to manipulate it directly. We are programmers, and are not limited by the constraints of the UI. I simply expose a selectTime(String value) function.

__Second__, this will produce a .png file containing a screen shot of the JPanel as a result. Simply [Approve The Result](#ApprovingTheResult) when itʼs ready.

__Thrid__, because these will render differently on different operating systems. These test automatically include a Machine Specific setting [NamerFactory.asOsSpecificTest()](https://github.com/approvals/ApprovalTests.Java/blob/master/java/org/approvaltests/namer/NamerFactory.java) which adds the os type (e.g: Mac_OS_X) to the file name

Generate Combinations of Parameter Values
---
To simplify getting more comprehensive sets of test cases, or expanding code coverage, ApprovalTests can generate combinations of possible parameters for a given function.

To do this, create an array of possible values for each parameter passed to a function (up to nine parameters). Call the CombinationApprovals.verifyAllCombinations() method passing the method to be called as a lambda. An example follows:

```java
    Integer[] points = new Integer[]{4, 5, 10};
    String[] words = new String[]{"Bookkeeper", "applesauce"};
    CombinationApprovals.verifyAllCombinations(
        (i, s) -> s.substring(0, i),
        points,
        words);
```

SampleTest.testSubstring.recieved.txt  
u
        [4, Bookkeeper] => Book
        [4, applesauce] => appl
        [5, Bookkeeper] => Bookk
        [5, applesauce] => apple
        [10, Bookkeeper] => Bookkeeper
        [10, applesauce] => applesauce

Here we are writing a single test that tries all 6 ( 3 ints * 2 String) combinations of inputs and the results those will produce.  

This will generate potentally hundreds or thousands of possible combinations of values. As before, the output will be displayed and, if the results are satisfactory, [Approve The Result](#ApprovingTheResult).

<a name='ApprovingTheResult'></a>
Approving The Result
---
When you run a test using ApprovalTests, it will generate a file named “YourTestClass.yourTestMethod.received.txt” (or .png, .html, etc.) and place it in the same directory as your test.

For the test to pass, this file must match:

YourTestClass.youTestMethod.__approved__.txt

There are many ways to do this:

1. Rename the .received file
2. Run the "move" command that is displayed (also added to your clipboard) in the command line
3. Use "use whole file" on a diff reporter
4. Use the "approve" command with the approval plugin (available for Eclipse)

Itʼs doesnʼt matter how you do it.

__Note__: If the files match, then the received file will be deleted.<br>
__Note__: You must include the received files in your source control.

Reporters
---
If an approval fails, then a report will be called that will report the “.received” and “.approved” files. There are many reporters, and you can create your own.

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

Supported Reporters
---
The following reporters are supported automatically. ApprovalTests will serach for the following applications in the following order on the respective platform:

Mac OSX        | Specified as
-----------    |---------
DiffMerge      | DIFF_MERGE
Beyond Compare | BEYOND_COMPARE
Kaleidescope   | KALEIDOSCOPE
KDiff3         | KDIFF3
P4Merge        | P4MERGE
TKDiff         | TK_DIFF

MS Windows          | Specified as
-----------         |---------
Beyond Compare 3    | BEYOND_COMPARE_3
Beyond Compare 4    | BEYOND_COMPARE_4
Tortoise Image Diff | TORTOISE_IMAGE_DIFF
Tortoise Merge      | TORTOISE_TEXT_DIFF
WinMerge            | WIN_MERGE_REPORTER
Araxis Merge        | ARAXIS_MERGE
Code Compare        | CODE_COMPARE
KDiff3              | KDIFF3

To use a reporter that is not in the above list, you will need to create a class of the following form:

```java
    public static class MyWinMergeReporter extends GenericDiffReporter {

        public static final MyWinMergeReporter INSTANCE     = new MyWinMergeReporter();
        static final String                       DIFF_PROGRAM = "C:\\Program Files (x86)\\WinMerge\\WinMergeU.exe";
        static final String                       MESSAGE      = MessageFormat.format(
                "Unable to find WinMerge at {0}", DIFF_PROGRAM);

        MyWinMergeReporter() {
            this(DIFF_PROGRAM, MESSAGE);
        }
        MyWinMergeReporter(String diffProgram, String diffProgramNotFoundMessage) {
            super(diffProgram, diffProgramNotFoundMessage);
        }
    }
```

To specify a particular reporter to be used for a test method, add the following annotation just before the test method definition:

    @UseReporter(MyWinMergeReporter.class)

Multiple reporters can be specified for a method as follows:

    @UseReporter({BEYOND_COMPARE, ClipboardReporter.class})
