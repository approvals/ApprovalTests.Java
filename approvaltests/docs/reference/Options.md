<a id="top"></a>

# Options

<!-- toc -->
## Contents

  * [Introduction](#introduction)
  * [Fluent Interface](#fluent-interface)
  * [Reporters](#reporters)
    * [Adding a Reporter](#adding-a-reporter)
  * [Scrubbers](#scrubbers)
  * [File Options](#file-options)
    * [File Extensions](#file-extensions)
    * [File Base Name](#file-base-name)
    * [File Name and Extension](#file-name-and-extension)
    * [Custom Namer](#custom-namer)
    * [Additional Information](#additional-information)
  * [Custom Comparators](#custom-comparators)
  * [Inline Approvals](#inline-approvals)
  * [Applying Options Functions](#applying-options-functions)
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

### Adding a Reporter

If you want to add a reporter alongside the existing one (rather than replacing it), use `addReporter()`.
This wraps both reporters in a `MultiReporter` so both are invoked on failure.

```java
options.addReporter(new TextWebReporter());
```

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
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/OptionsSamplesTest.java#L24-L26' title='Snippet source file'>snippet source</a> | <a href='#snippet-basic_approval_with_file_extension' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

**Note:** `withExtension()` returns an `Options` object, so it's possible to keep appending more `with...()` calls.

### File Base Name

To override just the base name of the approved/received files (keeping the current extension and directory), use `withBaseName()`.

```java
Approvals.verify("text", new Options().forFile().withBaseName("my_custom_name"));
```

### File Name and Extension

To override both the base name and extension at once, use `withName()`.

```java
Approvals.verify("text", new Options().forFile().withName("my_custom_name", ".json"));
```

### Custom Namer

To supply a fully custom `ApprovalNamer` (controlling both the directory and file name), use `withNamer()`.

```java
Approvals.verify("text", new Options().forFile().withNamer(myCustomNamer));
```

### Additional Information

To append extra context to the file name (useful for parameterised tests), use `withAdditionalInformation()`.

```java
Approvals.verify("text", new Options().forFile().withAdditionalInformation("scenario_1"));
```

## Custom Comparators
By default Approval Tests will only check if the `.approved` and `.received` files are _exactly_ matching.
The only accomodations it makes is for differences in line endings.

If you would like to create a more flexible comparison, you can do it by using the `Options.withComparator()` function.

## Inline Approvals

`inline()` lets you embed the expected value directly in your test source code rather than in a separate `.approved` file.

```java
Approvals.verify("hello world", new Options().inline("hello world"));
```

When the received output differs from the expected string, Approval Tests can update the source file automatically (depending on your `InlineOptions` settings).

## Applying Options Functions

The `and()` method applies a function that transforms an `Options` object. This is useful for extracting reusable option configurations.

```java
Function1<Options, Options> myOptions = o -> o.withReporter(new ReportNothing());
Approvals.verify("text", new Options().and(myOptions));
```

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
