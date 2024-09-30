package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;

public class MacDiffReporter extends FirstWorkingReporter
{
  public static final MacDiffReporter INSTANCE = new MacDiffReporter();
  // @formatter:off
  public MacDiffReporter()
  {
    super(
        // begin-snippet: mac_diff_reporters
        BeyondCompareMacReporter.INSTANCE,
        DiffMergeMacOsReporter.INSTANCE,
        KaleidoscopeDiffReporter.INSTANCE,
        P4MergeReporter.INSTANCE,
        AraxisMergeReporter.INSTANCE,
        KDiff3MacReporter.INSTANCE,
        TkDiffReporter.INSTANCE,
        IntelliJReporter.INSTANCE,
        VisualStudioCodeReporter.INSTANCE
        // end-snippet
    );
  }
  // @formatter:on
}
