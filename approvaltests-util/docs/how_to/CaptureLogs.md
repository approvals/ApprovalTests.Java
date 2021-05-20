<a id="top"></a>

# How to easily capture logs using SimpleLogger

<!-- toc -->
<!-- endToc -->
## Why use logs?
Logs can be a good way to capture both flow of a program and state as the program progresses. Adding logs to a program 
is also extremely safe which allows you to get regression tests in place.

## Capturing logs in test
By default logging goes to System.out. When capturing for testing, you will want them to instead go to a local StringBuffer.
Here's how to do it using the SimpleLogger:

snippet: log_to_string
