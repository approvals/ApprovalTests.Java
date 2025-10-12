<a id="top"></a>

# How to easily capture logs using SimpleLogger

<!-- toc -->
## Contents

  * [Why use logs?](#why-use-logs)
  * [Capturing logs in test](#capturing-logs-in-test)
  * [See also](#see-also)<!-- endToc -->


## Why use logs?
Logs can be a good way to capture both flow of a program and state as the program progresses. Adding logs to a program 
is also extremely safe which allows you to get regression tests in place.

## Capturing logs in test
By default logging goes to System.out. When capturing for testing, you will want them to instead go to a local StringBuffer.
Here's how to do it using the SimpleLogger:

<!-- snippet: log_to_string -->
<a id='snippet-log_to_string'></a>
```java
StringBuffer log = SimpleLogger.logToString();
new Sample().methodThatLogs();
Approvals.verify(log);
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/logger/SimpleLoggerTest.java#L36-L40' title='Snippet source file'>snippet source</a> | <a href='#snippet-log_to_string' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## See also
* [SimpleLogger](../reference/SimpleLogger.md#top)
