<a id="top"></a>

# ApprovalTests

<!-- toc -->
## Contents

  * [Features](#features)
  * [How Tos](#how-tos)
  * [Reference](#reference)
  * [Configuration](#configuration)
  * [Explanations](#explanations)
  * [Main concepts for ApprovalTests](#main-concepts-for-approvaltests)<!-- endToc -->

## Features

[New Features documented here](Features.md#top)

## How Tos

* use [Printable Wrappers](how_to/PrintableWrappers.md#top)
* use [Parameterized Tests](how_to/ParameterizedTest.md#top)
* use [MachineNameSpecific Tests](how_to/MachineNameSpecificTest.md#top)
* verify [Images](reference/AwtApprovals.md#top)
* remove inconsistent output using [Scrubbers](Scrubbers.md#top)
* have [Consistent Time Zones](how_to/ConsistentTimeZones.md#top) in your tests 
* separate and test [Data Access and Rendering](how_to/PatternsForTestingDataAccessAndRendering.md#top)
* show [nulls in verifyAsJson](how_to/ShowNullsInJson.md#top)
* [test a variety of values](reference/CombinationApprovals.md)
* [capture .received. files from CI](how_to/CaptureFilesFromCI.md)
* Create [customized verify methods](how_to/CreateCustomizedVerifyMethods.md)
* [Use Inline Approvals](how_to/InlineApprovals.md)
* Use [@TestFactory](how_to/UseTestFactory.md)

## Reference

* [AWTApprovals](reference/AwtApprovals.md)
* [Approval File Names](reference/Naming.md)
* [CombinationApprovals](reference/CombinationApprovals.md)
* [FrontLoadedReporters](reference/FrontLoadedReporter.md)
* [Naming of `.approved.` files](reference/Naming.md)
* [Options](reference/Options.md)
* [PackageSettings](reference/PackageSettings.md)
* [Story Boards](reference/StoryBoard.md)
* [The Verify API](reference/Verify.md)

## Configuration

Configuration of ApprovalTests mainly occurs via @Annotations, PackageSettings, and Options. 
[Read More about it here](Configuration.md#top)

**Note:** We are following [Daniele Procida's theory of documentation](https://documentation.divio.com)

## Explanations
* [Why we don't use checked exceptions](explanations/NoCheckedExceptions.md)
* [Best configuration practices](explanations/BestConfigurationPractices.md)
* [Inline Approvals Workflow](explanations/InlineApprovalsWorkflow.md)
* [Approval Testing Concepts](https://github.com/approvals/ApprovalTests.Documentation/blob/main/explanations/approval_testing.md)
* [Place to modify output](https://github.com/approvals/ApprovalTests.Documentation/blob/main/explanations/creating_output.md)
* [What are Approvals](https://github.com/approvals/ApprovalTests.Documentation/blob/main/explanations/what_are_approvals.md)
* [Overview of Approval Scripts](https://github.com/approvals/ApprovalTests.CommonScripts/blob/main/docs/explanations/overview.md)




## Main concepts for ApprovalTests  

1. **Verify** [verify(object)](reference/Verify.md) is the main pattern to use in ApprovalTests.  
1. **Options** allow you to [configure many aspects of Approvals](reference/Options.md)  
1. **[Reporters](reference/Reporters.md#top)** (often diff tools) are called on test failure to help you determine what went wrong.  
1. **Writers** write to a file  
1. **Namers** figure out what the file should be called and where it is located  

---

[Back to User Guide](README.md#top)
