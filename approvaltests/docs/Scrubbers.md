<a id="top"></a>

# Scrubbers

<!-- toc -->
## Contents

  * [Interface](#interface)
  * [Configuring Scrubbers](#configuring-scrubbers)
  * [Guid Scrubbing](#guid-scrubbing)
  * [RegEx Scrubbing](#regex-scrubbing)
  * [Date Scrubbing](#date-scrubbing)
    * [How to do it](#how-to-do-it)
    * [Supported formats](#supported-formats)
  * [Scrubbing multiple parts of a string](#scrubbing-multiple-parts-of-a-string)
  * [Using templates](#using-templates)<!-- endToc -->

![scrubber flow](https://raw.githubusercontent.com/approvals/ApprovalTests.cpp/master/doc/images/ScrubberOverview.png)

If you are having trouble getting tests running reproducibly, you might need to use a “scrubber” to convert the non-deterministic text to something stable.

## Interface

Fundamentally, a scrubber is function that takes a string and returns a string. 
You can create ones by passing in a function or a lambda. 
We also have some pre-made ones for your convenience.

## Configuring Scrubbers

You can configure all of the `Approvals.verify` methods to use scrubbers via the `Options` parameter.

For example:
<!-- snippet: guid-scrubbing-2 -->
<a id='snippet-guid-scrubbing-2'></a>
```java
Approvals.verifyAll("guids", guids, new Options(Scrubbers::scrubGuid));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/ScrubberTest.java#L42-L44' title='Snippet source file'>snippet source</a> | <a href='#snippet-guid-scrubbing-2' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

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
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/ScrubberTest.java#L35-L41' title='Snippet source file'>snippet source</a> | <a href='#snippet-guid-scrubbing-1' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
You can make this output deterministic by using a scrubber in the options.
For example:
<!-- snippet: guid-scrubbing-2 -->
<a id='snippet-guid-scrubbing-2'></a>
```java
Approvals.verifyAll("guids", guids, new Options(Scrubbers::scrubGuid));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/ScrubberTest.java#L42-L44' title='Snippet source file'>snippet source</a> | <a href='#snippet-guid-scrubbing-2' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
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
<!-- snippet: /src/test/java/org/approvaltests/scrubbers/RegExScrubberTest.replaceRandomNumber.approved.txt -->
<a id='snippet-/src/test/java/org/approvaltests/scrubbers/RegExScrubberTest.replaceRandomNumber.approved.txt'></a>
```txt
Hello [number] World!
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/RegExScrubberTest.replaceRandomNumber.approved.txt#L1-L1' title='Snippet source file'>snippet source</a> | <a href='#snippet-/src/test/java/org/approvaltests/scrubbers/RegExScrubberTest.replaceRandomNumber.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Date Scrubbing

### How to do it

The easiest way to scrub a date is by calling 
<!-- snippet: scrub-date-example -->
<a id='snippet-scrub-date-example'></a>
```java
Approvals.verify("created at 03:14:15", new Options().withScrubber(DateScrubber.getScrubberFor("00:00:00")));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/DateScrubberTest.java#L49-L51' title='Snippet source file'>snippet source</a> | <a href='#snippet-scrub-date-example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

which will produce

<!-- snippet: DateScrubberTest.exampleForDocumentation.approved.txt -->
<a id='snippet-DateScrubberTest.exampleForDocumentation.approved.txt'></a>
```txt
created at [Date1]
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/DateScrubberTest.exampleForDocumentation.approved.txt#L1-L1' title='Snippet source file'>snippet source</a> | <a href='#snippet-DateScrubberTest.exampleForDocumentation.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Supported formats

<!-- include: DateScrubberTest.supportedFormats.approved.md -->

| Example Date                   | RegEx Pattern                                                                                                                       |
| :----------------------------- | :---------------------------------------------------------------------------------------------------------------------------------- |
| Tue May 13 16:30:00            | [a-zA-Z]{3} [a-zA-Z]{3} \d{2} \d{2}:\d{2}:\d{2}                                                                                     |
| Wed Nov 17 22:28:33 EET 2021   | [a-zA-Z]{3} [a-zA-Z]{3} \d{2} \d{2}:\d{2}:\d{2} [a-zA-Z]{3,4} \d{4}                                                                 |
| Wed, 21 Oct 2015 07:28:00 GMT  | (Mon\|Tue\|Wed\|Thu\|Fri\|Sat\|Sun), \d{2} (Jan\|Feb\|Mar\|Apr\|May\|Jun\|Jul\|Aug\|Sep\|Oct\|Nov\|Dec) \d{4} \d{2}:\d{2}:\d{2} GMT |
| Tue May 13 2014 23:30:00.789   | [a-zA-Z]{3} [a-zA-Z]{3} \d{2} \d{4} \d{2}:\d{2}:\d{2}.\d{3}                                                                         |
| Tue May 13 16:30:00 -0800 2014 | [a-zA-Z]{3} [a-zA-Z]{3} \d{2} \d{2}:\d{2}:\d{2} -\d{4} \d{4}                                                                        |
| 13 May 2014 23:50:49,999       | \d{2} [a-zA-Z]{3} \d{4} \d{2}:\d{2}:\d{2},\d{3}                                                                                     |
| Oct 13 15:29                   | [A-Za-z]{3} \d{2} \d{2}:\d{2}                                                                                                       |
| May 13, 2014 11:30:00 PM PST   | [a-zA-Z]{3} \d{2}, \d{4} \d{2}:\d{2}:\d{2} [a-zA-Z]{2} [a-zA-Z]{3}                                                                  |
| 23:30:00                       | \d{2}:\d{2}:\d{2}                                                                                                                   |
| 2014/05/13 16:30:59.786        | \d{4}/\d{2}/\d{2} \d{2}:\d{2}:\d{2}(\.\d{3})?                                                                                       |
| 2020-9-10T08:07Z               | \d{4}-\d{1,2}-\d{1,2}T\d{1,2}:\d{2}Z                                                                                                |
| 2020-09-10T08:07:89Z           | \d{4}-\d{1,2}-\d{1,2}T\d{1,2}:\d{2}:\d{2}Z                                                                                          |
| 2020-09-10T01:23:45.678Z       | \d{4}-\d{1,2}-\d{1,2}T\d{1,2}:\d{2}\:\d{2}\.\d{3}Z                                                                                  |
| 20210505T091112Z               | \d{8}T\d{6}Z                                                                                                                        |
| 2024-12-17                     | \d{4}-\d{2}-\d{2}                                                                                                                   |
| 2024-12-18T14:04:46.746130Z    | \d{4}-\d{1,2}-\d{1,2}T\d{1,2}:\d{2}:\d{2}(\.\d{1,9})?Z                                                                              |
| 13/05/2014 23:50:49            | \d{2}[-/.]\d{2}[-/.]\d{4}\s\d{2}:\d{2}(:\d{2})?( (?:pm\|am\|PM\|AM))?                                                               |
| 2025-05-15 16:57:04.599        | \d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}\.\d+                                                                                            |

<!-- endInclude -->

## Scrubbing multiple parts of a string

If you need to do scrubbing of multiple things, the easiest way is to create multiple scrubbers and then combine them.

<!-- snippet: MultiScrubber -->
<a id='snippet-MultiScrubber'></a>
```java
final Scrubber portScrubber = new RegExScrubber(":\\d+/", ":[port]/");
final Scrubber dateScrubber = DateScrubber.getScrubberFor("20210505T091112Z");
final Scrubber signatureScrubber = new RegExScrubber("Signature=.+", "Signature=[signature]");
Scrubber scrubber = Scrubbers.scrubAll(portScrubber, dateScrubber, signatureScrubber);
Approvals.verify("http://127.0.0.1:55079/foo/bar?Date=20210505T091112Z&Signature=4a7dd6f09c1e",
    new Options(scrubber));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/ScrubberTest.java#L49-L56' title='Snippet source file'>snippet source</a> | <a href='#snippet-MultiScrubber' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

will result in

<!-- snippet: ScrubberTest.scrubMultipleThings.approved.txt -->
<a id='snippet-ScrubberTest.scrubMultipleThings.approved.txt'></a>
```txt
http://127.0.0.1:[port]/foo/bar?Date=[Date1]&Signature=[signature]
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/scrubbers/ScrubberTest.scrubMultipleThings.approved.txt#L1-L1' title='Snippet source file'>snippet source</a> | <a href='#snippet-ScrubberTest.scrubMultipleThings.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Using templates

`Scrubbers.Templates` contains many templates that can be inlined.

---

[Back to User Guide](README.md#top)
