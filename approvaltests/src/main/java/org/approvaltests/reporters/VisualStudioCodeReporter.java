package org.approvaltests.reporters;

public class VisualStudioCodeReporter extends FirstWorkingReporter
{
  public static final VisualStudioCodeReporter INSTANCE = new VisualStudioCodeReporter();
  public VisualStudioCodeReporter()
  {
    super(org.approvaltests.reporters.windows.VisualStudioCodeReporter.INSTANCE,
        org.approvaltests.reporters.macosx.VisualStudioCodeReporter.INSTANCE);
  }
}
