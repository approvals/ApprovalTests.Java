<!--
This file was generate by MarkdownSnippets.
Source File: /approvaltests/docs/mdsource/Features.source.md
To change this file edit the source file and then re-run the generation using either the dotnet global tool (https://github.com/SimonCropp/MarkdownSnippets#markdownsnippetstool) or using the api (https://github.com/SimonCropp/MarkdownSnippets#running-as-a-unit-test).
-->
<a id="top"></a>

# Features



<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Contents**

- [PackageLevelSettings](#packagelevelsettings)
- [Arlos Git Notation Prompt](#arlos-git-notation-prompt)
- [Faster Test Commit Revert (TCR)](#faster-test-commit-revert-tcr)
- [Test Commit Revert (TCR)](#test-commit-revert-tcr)
  - [Method 1 - extend class](#method-1---extend-class)
  - [Method 2 - use Runner](#method-2---use-runner)
  - [Results](#results)
  - [Usage](#usage)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## PackageLevelSettings

Package Level Settings allows for programmatic setting of configuration at the package level. It follows the principle of least surprise.   

Your Package Leveling configuration must be in class called PackageSettings. The fields can be private, public and or static. They will be picked up regardless. All methods will be ignored.

For example if you had a class:

<!-- snippet: /approvaltests/src/test/java/org/packagesettings/PackageSettings.java -->
```java
package org.packagesettings;

public class PackageSettings
{
  public String        name     = "Llewellyn";
  private int          rating   = 10;
  public static String lastName = "Falco";
}

```
<sup>[snippet source](/approvaltests/src/test/java/org/packagesettings/PackageSettings.java#L1-L9)</sup>
<!-- endsnippet -->

If you where to call at the org.packagesettings level.

<!-- snippet: package_level_settings_get -->
```java
Map<String, Settings> settings = PackageLevelSettings.get();
```
<sup>[snippet source](/approvaltests/src/test/java/org/packagesettings/PackageSettingsTest.java#L13-L15)</sup>
<!-- endsnippet -->

Then you would get the following settings

<!-- snippet: /approvaltests/src/test/java/org/packagesettings/PackageSettingsTest.testRetriveValue.approved.txt -->
```txt
lastName : Falco [from org.packagesettings.PackageSettings] 
name : Llewellyn [from org.packagesettings.PackageSettings] 
rating : 10 [from org.packagesettings.PackageSettings] 

```
<sup>[snippet source](/approvaltests/src/test/java/org/packagesettings/PackageSettingsTest.testRetriveValue.approved.txt#L1-L4)</sup>
<!-- endsnippet -->

However, if you also had

<!-- snippet: /approvaltests/src/test/java/org/packagesettings/subpackage/PackageSettings.java -->
```java
package org.packagesettings.subpackage;

public class PackageSettings
{
  public String   name        = "Test Name";
  private boolean rating      = true;
  public String   ratingScale = "logarithmic";
}

```
<sup>[snippet source](/approvaltests/src/test/java/org/packagesettings/subpackage/PackageSettings.java#L1-L9)</sup>
<!-- endsnippet -->

and you ran the same code but from the org.packagesettings.subpackage  
then you would get a blended view of the two classes where anything in the sub-package would override the parents.

<!-- snippet: /approvaltests/src/test/java/org/packagesettings/subpackage/PackageSettingsTest.testRetriveValueWithOverRide.approved.txt -->
```txt
lastName : Falco [from org.packagesettings.PackageSettings] 
name : Test Name [from org.packagesettings.subpackage.PackageSettings] 
rating : true [from org.packagesettings.subpackage.PackageSettings] 
ratingScale : logarithmic [from org.packagesettings.subpackage.PackageSettings] 

```
<sup>[snippet source](/approvaltests/src/test/java/org/packagesettings/subpackage/PackageSettingsTest.testRetriveValueWithOverRide.approved.txt#L1-L5)</sup>
<!-- endsnippet -->


## Arlos Git Notation Prompt

This is a prompt that includes helper text and quick actions for use with [Arlo's Commit Notation](https://github.com/RefactoringCombos/ArlosCommitNotation).
It is currently the default option for TCR and will produce a window that looks like:

![prompt](/approvaltests/src/test/java/machine_specific_tests/approvaltests/testcommitrevert/ArlosGitNotationPromptTest.test.Mac_OS_X.approved.png)



## Faster Test Commit Revert (TCR)

The startup time for swing is around 3 seconds (on my mac). This is painfully slow in some situations. 
FasterTestCommitRevertRunner & FasterTestCommitRevertTest Solve this by shelling out to applescript, which 
brings the time down to a few milliseconds. 

Note: this currently only works on mac and will default to the normal swing otherwise. 

## Test Commit Revert (TCR)

This is a convenience class to follow the practice 'Test Commit/Revert' from Kent Beck. Simply adding
### Method 1 - extend class
<!-- snippet: test_commit_revert -->
```java
extends TestCommitRevertTest
```
<sup>[snippet source](/approvaltests/src/test/java/org/approvaltests/testcommitrevert/TestCommitRevertSample.java#L12-L14)</sup>
<!-- endsnippet -->
to your test class

### Method 2 - use Runner
<!-- snippet: test_commit_revert_runner -->
```java
@RunWith(TestCommitRevertRunner.class)
public class TestCommitRevertSample
```
<sup>[snippet source](/approvaltests/src/test/java/org/approvaltests/testcommitrevert/TestCommitRevertSample.java#L8-L11)</sup>
<!-- endsnippet -->
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
