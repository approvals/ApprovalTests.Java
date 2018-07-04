package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.FirstWorkingReporter;

public class WindowsDiffReporter extends FirstWorkingReporter
{
  public static final WindowsDiffReporter INSTANCE = new WindowsDiffReporter();
  public WindowsDiffReporter()
  {
    super(TortoiseDiffReporter.INSTANCE, BeyondCompareReporter.INSTANCE, VisualStudioCodeReporter.INSTANCE,
        WinMergeReporter.INSTANCE, AraxisMergeReporter.INSTANCE, CodeCompareReporter.INSTANCE,
        KDiff3Reporter.INSTANCE);
  }
}
