<a id="top"></a>

# Options

<!-- toc -->
## Contents

* [Introduction](#introduction)
* [Fluent Interface](#fluent-interface)
* [Reporters](#reporters)
* [Scrubbers](#scrubbers)
* [File Options](#file-options)
    * [File Extensions](#file-extensions)
* [Defaults](#defaults)
* [Adding to Existing Options object](#adding-to-existing-options-object)<!-- endToc -->

## Introduction

There are many things you might want to tweak with Approval Tests. `Options` is the entry-point for many of the changes.
It is on all `verify()` methods, as an optional parameter.

## Fluent Interface

`Options` utilizes a fluent interface, allowing you to chain together commands. Each returned object is a new copy.

<!-- snippet: specify_all_the_options -->
<a id='snippet-specify_all_the_options'></a>
```cpp
ApprovalTests::Options()
    .withReporter(ApprovalTests::QuietReporter())
    .withScrubber(ApprovalTests::Scrubbers::scrubGuid)
    .fileOptions().withFileExtension(".json")
```
<sup><a href='/tests/DocTest_Tests/DocTestApprovalTestTests.cpp#L50-L55' title='Snippet source file'>snippet source</a> | <a href='#snippet-specify_all_the_options' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Reporters

[Reporters](/doc/Reporters.md#top) launch diff tools upon failure.  Read how to configure them with `Options` [here](../Reporters.md#via-options):

## Scrubbers

[Scrubbers](/doc/explanations/Scrubbers.md#top) clean output to help remove inconsistent pieces of text, such as dates.  Read how to set up Scrubbers [here](../Scrubbers.md#configuring-scrubbers):
There are two ways to set a Scrubber.

## File Options

The `Options::FileOptions` class exists to customise the `.approved` and `.received` files in various ways.

For now, it just controls the file extension.

### File Extensions

If you want to change the file extension of both the approved and received files, use `withFileExtension()`.

<!-- snippet: basic_approval_with_file_extension -->
<a id='snippet-basic_approval_with_file_extension'></a>
```cpp
using namespace ApprovalTests;

Approvals::verify("text to be verified",
                  Options().fileOptions().withFileExtension(".xyz"));
```
<sup><a href='/tests/DocTest_Tests/core/OptionsExamples.cpp#L7-L12' title='Snippet source file'>snippet source</a> | <a href='#snippet-basic_approval_with_file_extension' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

**Note:** `withFileExtension()` returns an `Options` object, so it's possible to keep appending more `with...()` calls.

## Defaults

The default constructor for Options does:

* no scrubbing
* uses file extension `.txt`
* uses whatever is currently set as [the default reporter](#registering-a-default-reporter).

## Adding to Existing Options object

Because of the fluent options, you can modify a specific option from an existing Options object,
while retaining all other settings, and not changing the original object.

```cpp
verifyWithQuietReporter(std::string text, const Options& o)
{
    Approvals::verify(text, o.withReporter(QuietReporter());
}
```

---

[Back to User Guide](/doc/README.md#top)