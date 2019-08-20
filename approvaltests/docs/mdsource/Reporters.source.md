<a id="top"></a>

# Reporters

toc

## Supported Diff Tools

The DiffReporter class goes through a chain of possible reporters to find the first option installed on your system.
Currently the search goes in this order:

### Mac

snippet: mac_diff_reporters


### Windows

snippet: windows_diff_reporters

### Linux

snippet: linux_diff_reporters

## Configuring a Reporter

You can configure which reporter to use in a few ways. ApprovalTests uses the method of principle of least surprise. Meaning the reporter uses is the one declared closest to the test. 

### Class and Method level

At both the class and method level you can use the @UseReporter attribute to set 1 or multiple reporters.

snippet: use_reporter_single

snippet: use_reporter_multiple 

*Note*: If you use multiple reporters ALL of them will report. Not just the first one.

### Package Level

You can also assign a default for an entire package (and all sub-packages) by creating a PackageSettings class. Here is an example  


snippet: use_reporter_package_settings

## Custom Reporters

If your favorite diff tool isn't supported out of the box, create your own custom extentions.
Here's an example:

snippet: custom_reporter


---

[Back to User Guide](README.md#top)
