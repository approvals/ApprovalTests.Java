package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.FirstWorkingReporter;

public class WindowsDiffReporter extends FirstWorkingReporter
{
  public static final WindowsDiffReporter INSTANCE = new WindowsDiffReporter();
  public WindowsDiffReporter()
  {
    super(TortoiseDiffReporter.INSTANCE, BeyondCompareReporter.INSTANCE, WinMergeReporter.INSTANCE);
  }
}
