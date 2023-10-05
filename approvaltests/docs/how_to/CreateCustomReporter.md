<a id="top"></a>

# How to Create Your Own Custom Reporter

<!-- toc -->
## Contents

  * [Introduction](#introduction)
  * [Problem Description](#problem-description)
  * [Solution: Using FileCaptureReporter](#solution-using-filecapturereporter)
  * [Compatibility](#compatibility)<!-- endToc -->

## Problem Statement
If your favorite diff tool isn't supported out of the box, you can create your own custom extensions.

### Example Code
Here's an example:

<!-- snippet: custom_reporter -->
<a id='snippet-custom_reporter'></a>
```java
public class CustomReporter extends GenericDiffReporter
{
  // optional singleton, but improves performance
  public static final CustomReporter INSTANCE = new CustomReporter();
  public CustomReporter()
  {
    super("/fullpath/to/diffProgram.exe");
  }
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/reporters/CustomReporter.java#L3-L13' title='Snippet source file'>snippet source</a> | <a href='#snippet-custom_reporter' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

See Also: [Reporters](../reference/Reporters.md)