<a id="top"></a>

# SimpleLogger

toc

## How SimpleLogger works
Unlike many logging frameworks, SimpleLogger is a combination of static methods that allows you to log based on event 
type rather than event level. This means you can turn on and off specific types of events that may not be interesting 
for test purposes, rather than setting a verbosity level. For example, you could say, "Show me all the SQL statements"
or "Show me all the method entries". Also, because SimpleLogger is tagging based on the event type, it has many 
convenience functions to properly format and call based on that format.

For example, here's how you would log entry and exit from a method:
<!-- snippet: simple_logger_use_markers -->
<a id='snippet-simple_logger_use_markers'></a>
```java
public void methodThatLogs()
{
  try (Markers markers = SimpleLogger.useMarkers())
  {
    for (int i = 0; i < 3; i++)
    {
      innerMethod(i);
    }
  }
}
private void innerMethod(int i)
{
  try (Markers markers = SimpleLogger.useMarkers())
  {
    SimpleLogger.variable("i", i);
  }
}
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/logger/Sample.java#L5-L23' title='Snippet source file'>snippet source</a> | <a href='#snippet-simple_logger_use_markers' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Will produce the following logs:
<!-- snippet: SimpleLoggerTest.testMarkers.approved.txt -->
<a id='snippet-SimpleLoggerTest.testMarkers.approved.txt'></a>
```txt
=> Sample.methodThatLogs() - IN
   => Sample.innerMethod() - IN
      Variable: i = '0'
   <= Sample.innerMethod() - OUT
   => Sample.innerMethod() - IN
      Variable: i = '1'
   <= Sample.innerMethod() - OUT
   => Sample.innerMethod() - IN
      Variable: i = '2'
   <= Sample.innerMethod() - OUT
<= Sample.methodThatLogs() - OUT
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/logger/SimpleLoggerTest.testMarkers.approved.txt#L1-L11' title='Snippet source file'>snippet source</a> | <a href='#snippet-SimpleLoggerTest.testMarkers.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## HowTos
* [How to capture logs for testing](../how_to/CaptureLogs.md#top)

## SimpleLogger.logToNothing()

SimpleLogger is used to make approvaltesting easier, but approvalTests also uses it internally. Something these bleed out in the form of messages like

<!-- snippet: /approvaltests-util-tests/src/test/java/com/spun/util/logger/SimpleLoggerTest.test.approved.txt -->
<a id='snippet-/approvaltests-util-tests/src/test/java/com/spun/util/logger/SimpleLoggerTest.test.approved.txt'></a>
```txt
=> SimpleLoggerTest.test() - IN
   => SimpleLoggerTest.test() - IN
      Event: Starting Logging
      Variable: name = 'llewellyn'
      Sql: Select * from people
      .........1.........2.........3.........4..
      Variable: Numbers.length = 5
      Numbers[0] = 1
      Numbers[1] = 2
      Numbers[2] = 3
      Numbers[3] = 4
      Numbers[4] = 5
******************************************************************************************
      
java.lang.Error -  null
******************************************************************************************
   <= SimpleLoggerTest.test() - OUT
<= SimpleLoggerTest.test() - OUT
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/logger/SimpleLoggerTest.test.approved.txt#L1-L18' title='Snippet source file'>snippet source</a> | <a href='#snippet-/approvaltests-util-tests/src/test/java/com/spun/util/logger/SimpleLoggerTest.test.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

if you want to turn them all off just run

<!-- snippet: log_nothing -->
<a id='snippet-log_nothing'></a>
```java
SimpleLogger.logToNothing();
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/logger/SimpleLoggerTest.java#L41-L43' title='Snippet source file'>snippet source</a> | <a href='#snippet-log_nothing' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
