package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.windows.IntelliJReporter;

public class MacDiffReporter extends FirstWorkingReporter
{
  public static final MacDiffReporter INSTANCE = new MacDiffReporter();
  public MacDiffReporter()
  {
    super(
        // startcode mac_diff_reporters
        BeyondCompareMacReporter.INSTANCE,
        DiffMergeReporter.INSTANCE,
        KaleidoscopeDiffReporter.INSTANCE,
        P4MergeReporter.INSTANCE,
        KDiff3Reporter.INSTANCE,
        TkDiffReporter.INSTANCE,
        IntelliJReporter.INSTANCE,
        VisualStudioCodeReporter.INSTANCE
    // endcode
    );
  }
}
