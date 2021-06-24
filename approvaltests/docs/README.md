<a id="top"></a>

# ApprovalTests

<!-- toc -->
## Contents

  * [Features](#features)
  * [How Tos](#how-tos)
  * [Configuration](#configuration)
  * [Main concepts for ApprovalTests](#main-concepts-for-approvaltests)<!-- endToc -->

## Features

[New Features documented here](Features.md#top)

## How Tos

* use [Printable Wrappers](how_to/PrintableWrappers.md#top)
* use [Parameterized Tests](how_to/ParameterizedTest.md#top)
* use [MachineNameSpecific Tests](how_to/MachineNameSpecificTest.md#top)
* verify [Images](reference/AwtApprovals.md#top)
* remove inconsistent output using [Scrubbers](Scrubbers.md#top)

## Configuration

Configuration of ApprovalTests mainly occurs via @Annotations and PackageSettings. 
[Read More about it here](Configuration.md#top)


## Main concepts for ApprovalTests  

[Reporters](Reporters.md#top) Namers & Writers are the 3 pieces that allow ApprovalTests to work. 
 
**Writers** write to a file  
**Namers** figure out what the file should be called and where it is located  
**Reporters** are called on failure to help you determine what went wrong.  

---

[Back to User Guide](README.md#top)
