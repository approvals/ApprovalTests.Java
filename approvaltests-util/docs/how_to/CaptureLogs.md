<a id="top"></a>

# How to easily capture logs using SimpleLogger

<!-- toc -->
## Contents

  * [Capturing logs in test](#capturing-logs-in-test)<!-- endToc -->


## Why use logs?
Logs can be a good way to capture both flow of a program and state as the program progresses. Adding logs to a program 
is also extremely safe which allows you to get regression tests in place.

## Capturing logs in test
By default logging goes to System.out. When capturing for testing, you will want them to instead go to a local StringBuffer.
Here's how to do it using the SimpleLogger:

<!-- snippet: log_to_string -->
<a id='snippet-log_to_string'></a>
```java
StringBuffer output = SimpleLogger.logToString();
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/logger/SimpleLoggerTest.java#L11-L13' title='Snippet source file'>snippet source</a> | <a href='#snippet-log_to_string' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## How SimpleLogger works
Unlike many logging frameworks, SimpleLogger is a combination of static methods that allows you to log based on event 
type rather than event level. This means you can turn on and off specific types of events that may not be interesting 
for test purposes, rather than setting a verbosity level. For example, you could say, "Show me all the SQL statements"
or "Show me all the method entries". Also, because SimpleLogger is tagging based on the event type, it has many 
convenience functions to properly format and call based on that format.

For example, here's how you would log entry and exit from a method:
snippet: simple_logger_use_markers

Will produce the following logs:
snippet: SimpleLoggerTest.testMarkers.approved.txt