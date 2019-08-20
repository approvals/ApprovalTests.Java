package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;

public class MacDiffReporter extends FirstWorkingReporter
{
  public static final MacDiffReporter INSTANCE = new MacDiffReporter();
  public MacDiffReporter()
  {
    super(
        // begin-snippet: mac_diff_reporters
        BeyondCompareMacReporter.INSTANCE,
        DiffMergeReporter.INSTANCE,
        KaleidoscopeDiffReporter.INSTANCE,
        P4MergeReporter.INSTANCE,
        KDiff3Reporter.INSTANCE,
        TkDiffReporter.INSTANCE,
        IntelliJReporter.INSTANCE,
        VisualStudioCodeReporter.INSTANCE
    // end-snippet
    );
  }
}
