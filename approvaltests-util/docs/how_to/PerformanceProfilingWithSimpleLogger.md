<a id="top"></a>

# How to do Performance Profiling using SimpleLogger

<!-- toc -->
## Contents

  * [Understanding timings in log output](#understanding-timings-in-log-output)<!-- endToc -->

## Understanding timings in log output
When SimpleLogger logs to a file or Standard.out it includes timings (these are turned off by default if you are logging
to a string as they are inconsistent for testing purposes.)

Here's an example of the output:
```.text
[May 20, 2021, 9:44:17 PM ~000002ms] => Sample.methodThatLogs() - IN
[May 20, 2021, 9:44:17 PM ~000047ms]    => Sample.innerMethod() - IN
[May 20, 2021, 9:44:17 PM ~000001ms]       Variable: i = '0'
[May 20, 2021, 9:44:17 PM ~000000ms]    <= Sample.innerMethod() - OUT
```
The important part here is `~000047ms` which represents the number of milliseconds that have passed since the previous line SimpleLogger 
 logged. This allows an easy way to detect bottlenecks by adding log statements and test improvements.

## See also
* [SimpleLogger](../reference/SimpleLogger.md#top)
