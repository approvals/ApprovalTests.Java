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
  * [Custom Comparators](#custom-comparators)
  * [Defaults](#defaults)
  * [Adding to Existing Options object](#adding-to-existing-options-object)<!-- endToc -->

## Introduction

There are many things you might want to tweak with Approval Tests. `Options` is the entry-point for many of the changes.
It is on all `verify()` methods, as an optional parameter.

## Fluent Interface

`Options` utilizes a fluent interface, allowing you to chain together commands. Each returned object is a new copy.

<!-- snippet: specify_all_the_options -->
<a id='snippet-specify_all_the_options'></a>
```java
new Options().withReporter(new ReportNothing()).withScrubber(new GuidScrubber()).forFile()
    .withExtension(".json");
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/OptionsSamplesTest.java#L15-L18' title='Snippet source file'>snippet source</a> | <a href='#snippet-specify_all_the_options' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Reporters

[Reporters](/docs/reference/Reporters.md#top) launch diff tools upon failure.  Read how to configure them with `Options` [here](Reporters.md#via-options):

## Scrubbers

[Scrubbers](/docs/explanations/Scrubbers.md#top) clean output to help remove inconsistent pieces of text, such as dates.  Read how to set up Scrubbers [here](../Scrubbers.md#configuring-scrubbers):
There are two ways to set a Scrubber.

## File Options

The `Options.forFile()` class exists to customise the `.approved` and `.received` files in various ways.

### File Extensions

If you want to change the file extension of both the approved and received files, use `withExtension()`.

<!-- snippet: basic_approval_with_file_extension -->
<a id='snippet-basic_approval_with_file_extension'></a>
```java
Approvals.verify("text to be verified", new Options().forFile().withExtension(".xyz"));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/OptionsSamplesTest.java#L23-L25' title='Snippet source file'>snippet source</a> | <a href='#snippet-basic_approval_with_file_extension' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

**Note:** `withExtension()` returns an `Options` object, so it's possible to keep appending more `with...()` calls.

## Custom Comparators
By default Approval Tests will only check if the `.approved` and `.received` files are _exactly_ matching.
The only accomodations it makes is for differences in line endings.

If you would like to create a more flexible comparison, you can do it by using the `Options.withComparator()` function.

## Defaults

The default constructor for Options does:

* no scrubbing
* uses file extension `.txt`
* uses whatever is currently set as the default reporter.

## Adding to Existing Options object

Each instance of Options is not mutable. Therefore, every call produces a new copy of the Options object. This allows you to modify a specific option from an existing Options object,
while retaining all other settings, and not changing the original object.


---

[Back to User Guide](../README.md#top)
