package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.FirstWorkingReporter;

public class WindowsDiffReporter extends FirstWorkingReporter
{
  public static final WindowsDiffReporter INSTANCE = new WindowsDiffReporter();
  public WindowsDiffReporter()
  {
    super(
        // startcode windows_diff_reporters
        TortoiseDiffReporter.INSTANCE,
        BeyondCompareReporter.INSTANCE,
        WinMergeReporter.INSTANCE,
        AraxisMergeReporter.INSTANCE,
        CodeCompareReporter.INSTANCE,
        KDiff3Reporter.INSTANCE,
        IntelliJReporter.INSTANCE,
        VisualStudioCodeReporter.INSTANCE
    // endcode
    );
  }
}
