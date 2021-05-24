<a id="top"></a>

# Scrubbers

<!-- toc -->
## Contents

  * [Interface](#interface)
  * [Guid Scrubbing](#guid-scrubbing)
  * [RegEx Scrubbing](#regex-scrubbing)
  * [Date Scrubbing](#date-scrubbing)
    * [How to do it](#how-to-do-it)
    * [Supported formats](#supported-formats)
  * [Using templates](#using-templates)<!-- endToc -->

![scrubber flow](https://raw.githubusercontent.com/approvals/ApprovalTests.cpp/master/doc/images/ScrubberOverview.png)

If you are having trouble getting tests running reproducibly, you might need to use a “scrubber” to convert the non-deterministic text to something stable.

## Interface

Fundamentally, a scrubber is function that takes a string and returns a string. 
You can create ones by passing in a function or a lambda. 
We also have some pre-made ones for your convenience.


## Guid Scrubbing

Let's say you have the following Guids in your output:
<!-- snippet: guid-scrubbing-1 -->
<a id='snippet-guid-scrubbing-1'></a>
```java
String[] guids = {"2fd78d4a-ad49-447d-96a8-deda585a9aa5",
                  "2fd78d4a-1111-1111-1111-deda585a9aa5",
                  "2fd78d4a-3333-3333-3333-deda585a9aa5",
                  "2fd78d4a-ad49-447d-96a8-deda585a9aa5",
                  "2fd78d4a-ad49-447d-96a8-deda585a9aa5 and text"};
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/ScrubberTest.java#L31-L37' title='Snippet source file'>snippet source</a> | <a href='#snippet-guid-scrubbing-1' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
You can make this output deterministic by using a scrubber in the options.
For example:
<!-- snippet: guid-scrubbing-2 -->
<a id='snippet-guid-scrubbing-2'></a>
```java
Approvals.verifyAll("guids", guids, new Options(Scrubbers::scrubGuid));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/ScrubberTest.java#L38-L40' title='Snippet source file'>snippet source</a> | <a href='#snippet-guid-scrubbing-2' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
**Note:** Options is available on all Approvals.verify methods.
This will result in the following `.approved.txt` file
<!-- snippet: /src/test/java/org/approvaltests/scrubbers/ScrubberTest.scrubGuids.approved.txt -->
<a id='snippet-/src/test/java/org/approvaltests/scrubbers/ScrubberTest.scrubGuids.approved.txt'></a>
```txt
guids[0] = guid_1
guids[1] = guid_2
guids[2] = guid_3
guids[3] = guid_1
guids[4] = guid_1 and text
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/ScrubberTest.scrubGuids.approved.txt#L1-L5' title='Snippet source file'>snippet source</a> | <a href='#snippet-/src/test/java/org/approvaltests/scrubbers/ScrubberTest.scrubGuids.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
**Note:** If a Guid is used in multiple places, it will be scrubbed using the same replacement.
That is why you see `guid_1` three times.

## RegEx Scrubbing

Using a regex search term

For example, here is an example where random numbers are scrubbed:
<!-- snippet: scrub-regex-example -->
<a id='snippet-scrub-regex-example'></a>
```java
String input = "Hello " + new Random().nextInt(100) + " World!";
Approvals.verify(input, new Options(new RegExScrubber("(\\d+)", "[number]")));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/RegExScrubberTest.java#L14-L17' title='Snippet source file'>snippet source</a> | <a href='#snippet-scrub-regex-example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
producing
<!-- snippet: /src/test/java/org/approvaltests/scrubbers/RegExScrubberTest.name.approved.txt -->
<a id='snippet-/src/test/java/org/approvaltests/scrubbers/RegExScrubberTest.name.approved.txt'></a>
```txt
Hello [number] World!
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/RegExScrubberTest.name.approved.txt#L1-L1' title='Snippet source file'>snippet source</a> | <a href='#snippet-/src/test/java/org/approvaltests/scrubbers/RegExScrubberTest.name.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Date Scrubbing

### How to do it

The easiest way to scrub a date is by calling 
<!-- snippet: scrub-date-example -->
<a id='snippet-scrub-date-example'></a>
```java
Approvals.verify("created at 03:14:15", new Options().withScrubber(DateScrubber.getScrubberFor("00:00:00")));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/DateScrubberTests.java#L46-L48' title='Snippet source file'>snippet source</a> | <a href='#snippet-scrub-date-example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

which will produce

<!-- snippet: DateScrubberTests.exampleForDocumentation.approved.txt -->
<a id='snippet-DateScrubberTests.exampleForDocumentation.approved.txt'></a>
```txt
created at [Date1]
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/DateScrubberTests.exampleForDocumentation.approved.txt#L1-L1' title='Snippet source file'>snippet source</a> | <a href='#snippet-DateScrubberTests.exampleForDocumentation.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Supported formats

<!-- include: DateScrubberTests.supportedFormats.approved.md -->
| Example Date | RegEx Pattern |
| :-------------------- | :----------------------- | 
| Tue May 13 16:30:00 | [a-zA-Z]{3} [a-zA-Z]{3} \d{2} \d{2}:\d{2}:\d{2} |
| Tue May 13 2014 23:30:00.789 | [a-zA-Z]{3} [a-zA-Z]{3} \d{2} \d{4} \d{2}:\d{2}:\d{2}.\d{3} |
| Tue May 13 16:30:00 -0800 2014 | [a-zA-Z]{3} [a-zA-Z]{3} \d{2} \d{2}:\d{2}:\d{2} -\d{4} \d{4} |
| 13 May 2014 23:50:49,999 | \d{2} [a-zA-Z]{3} \d{4} \d{2}:\d{2}:\d{2},\d{3} |
| May 13, 2014 11:30:00 PM PST | [a-zA-Z]{3} \d{2}, \d{4} \d{2}:\d{2}:\d{2} [a-zA-Z]{2} [a-zA-Z]{3} |
| 23:30:00 | \d{2}:\d{2}:\d{2} |
| 2014/05/13 16:30:59.786 | \d{4}/\d{2}/\d{2} \d{2}:\d{2}:\d{2}.\d{2}\d |
| 2020-9-10T08:07Z | \d{4}-\d{1,2}-\d{1,2}T\d{1,2}:\d{2}Z |
| 2020-09-10T08:07:89Z | \d{4}-\d{1,2}-\d{1,2}T\d{1,2}:\d{2}:\d{2}Z |
| 2020-09-10T01:23:45.678Z | \d{4}-\d{1,2}-\d{1,2}T\d{1,2}:\d{2}\:\d{2}\.\d{3}Z |
<!-- endInclude -->

## Scrubbing multiple parts of a string

If you need to do scrubbing of multiple things, the easiest way is to create multiple scrubbers and then combine them.

snippet: MultiScrubber

will result in

snippet: ScrubberTest.scrubMultipleThings.approved.txt

## Using templates

`Scrubbers.Templates` contains many templates that can be inlined.

---

[Back to User Guide](README.md#top)
