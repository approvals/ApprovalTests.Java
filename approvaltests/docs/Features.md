<a id="top"></a>

# Features

<!-- toc -->
## Contents

  * [9.4.0](#940)
    * [CombinationApprovals.verifyBestCoveringPairs](#combinationapprovalsverifybestcoveringpairs)
  * [9.3.0](#930)
    * [Printable wrappers](#printable-wrappers)
  * [6.0.1](#601)
    * [Prepare for Android](#prepare-for-android)
  * [Previous versions](#previous-versions)
  * [Customer Approvers](#customer-approvers)
  * [Using Alternative Base Directory for Approval Output Files](#using-alternative-base-directory-for-approval-output-files)
  * [Using Subdirectories for Approval Output Files](#using-subdirectories-for-approval-output-files)
  * [PackageLevelSettings](#packagelevelsettings)
  * [Arlos Git Notation Prompt](#arlos-git-notation-prompt)
  * [Faster Test Commit Revert (TCR)](#faster-test-commit-revert-tcr)
  * [Test Commit Revert (TCR)](#test-commit-revert-tcr)
    * [Method 1 - extend class](#method-1---extend-class)
    * [Method 2 - use Runner](#method-2---use-runner)
    * [Results](#results)
    * [Usage](#usage)<!-- endToc -->

## 9.4.0
### CombinationApprovals.verifyBestCoveringPairs
This takes advantage of a concept called [pairwise testing](https://youtu.be/xzs-Zpz8vPg?t=134) to dramatically reduce the amount of combinations executed while retaining the vast majority of coverage.
This can be very useful when running long running tests or when dealing with vast amounts of combinations.
All you need to do in order to use this, is change `CombinationApprovals.verifyAllCombinations` to `CombinationApprovals.verifyBestCoveringPairs`.

Notice that for low numbers of parameters, very little is achieved, but at high numbers, the results are amazing.

 <!-- include: PairWiseTest.forTable.approved. path: /approvaltests-tests/src/test/java/org/approvaltests/combinations/PairWiseTest.forTable.approved.include.md -->

| Number of Parameters | Variations per Parameter | Total Combinations | Pairwise Combinations |
| --------------------: | -----------------------: | ------------------: | ---------------------: |
|2|5|25|25|
|3|3|27|11|
|3|4|64|16|
|4|5|625|33|
|5|6|7,776|49|
|9|9|387,420,489|134|

 <!-- endInclude -->



## 9.3.0
### Printable wrappers

See [How to customize another objects toString()](/approvaltests/docs/how_to/PrintableWrappers.md)

## 6.0.1
### Prepare for Android
We have deprecated:
* `Approvals.verify(Image image)`
* `Approvals.verify(BufferedImage bufferedImage)`
* `Approvals.verify(Component c)`

These are now in the AwtApprovals class.

## Previous versions


## Customer Approvers

If you want to customize how approvaltests decides if two files are equal, you can pass in your own deciding function.

<!-- snippet: custom_approver -->
<a id='snippet-custom_approver'></a>
```java
ApprovalTextWriter writer = new ApprovalTextWriter("Random: ", new Options());
ApprovalNamer namer = Approvals.createApprovalNamer();
Function2<File, File, Boolean> approveEverything = (r, a) -> true;
Approvals.verify(new FileApprover(writer, namer, approveEverything));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/approvers/FileApproverTest.java#L65-L70' title='Snippet source file'>snippet source</a> | <a href='#snippet-custom_approver' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->


## Using Alternative Base Directory for Approval Output Files

**Q:** what if I want my output files to show up under the `/resources/` folder?

**A:** you can do that, [here's how](Configuration.md#alternative-base-directory-for-output-files)


## Using Subdirectories for Approval Output Files
Approved and received files can be stored in a preferred location. 
[More here](Configuration.md#using-subdirectories-for-approval-output-files)

## PackageLevelSettings

Package Level Settings allows for programmatic setting of configuration at the package level.
[More Here](Configuration.md#packagelevelsetting)

## Arlos Git Notation Prompt

This is a prompt that includes helper text and quick actions for use with [Arlo's Commit Notation](https://github.com/RefactoringCombos/ArlosCommitNotation).
It is currently the default option for TCR and will produce a window that looks like:

![prompt](/approvaltests-tests/src/test/java/org/approvaltests/machine_specific_tests/testcommitrevert/ArlosGitNotationPromptTest.test.Mac_OS_X.approved.png)



## Faster Test Commit Revert (TCR)

The startup time for swing is around 3 seconds (on my mac). This is painfully slow in some situations. 
FasterTestCommitRevertRunner & FasterTestCommitRevertTest Solve this by shelling out to applescript, which 
brings the time down to a few milliseconds. 

Note: this currently only works on mac and will default to the normal swing otherwise. 

## Test Commit Revert (TCR)

This is a convenience class to follow the practice 'Test Commit/Revert' from Kent Beck. Simply adding
### Method 1 - extend class
<!-- snippet: test_commit_revert -->
<a id='snippet-test_commit_revert'></a>
```java
extends TestCommitRevertTest
```
<sup><a href='/approvaltests/src/test/java/org/approvaltests/testcommitrevert/TestCommitRevertSample.java#L12-L14' title='Snippet source file'>snippet source</a> | <a href='#snippet-test_commit_revert' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
to your test class

### Method 2 - use Runner
<!-- snippet: test_commit_revert_runner -->
<a id='snippet-test_commit_revert_runner'></a>
```java
@RunWith(TestCommitRevertRunner.class)
public class TestCommitRevertSample
```
<sup><a href='/approvaltests/src/test/java/org/approvaltests/testcommitrevert/TestCommitRevertSample.java#L8-L11' title='Snippet source file'>snippet source</a> | <a href='#snippet-test_commit_revert_runner' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
to annotate your class

### Results 

Whichever method you use, on test run TRC will invoke 1 of 2 options

*  On success
A dialog will appear asking for a commit message. Once given it will commit all files with that message
![prompt](images/commit_dialog.png)

* On failure
It will revert all changes

Note: this currently only works with git

### Usage

Use this with caution. I find Test Commit/Revert helpful when doing a strict refactoring. It is unusable when creating approvaltests as the first run always fails and gets reverted.
Often I will have a test extend TCR for short periods then remove it after I'm out of a refactoring mode.

This also works well with [Arlo Belshee's Git Notation](https://github.com/RefactoringCombos/ArlosCommitNotation) 


---

[Back to User Guide](README.md#top)
