package org.approvaltests.reporters;

// begin-snippet: custom_reporter
public class CustomReporter extends GenericDiffReporter
{
  // optional singleton, but improves performance
  public static final CustomReporter INSTANCE = new CustomReporter();
  public CustomReporter()
  {
    super("/fullpath/to/diffProgram.exe");
  }
}
// end-snippet
