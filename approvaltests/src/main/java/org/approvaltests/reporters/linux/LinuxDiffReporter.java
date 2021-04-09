package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;
import org.approvaltests.reporters.macosx.DiffMergeReporter;

public class LinuxDiffReporter extends FirstWorkingReporter
{
  public static final LinuxDiffReporter INSTANCE = new LinuxDiffReporter();
  public LinuxDiffReporter()
  {
    super(
        // begin-snippet: linux_diff_reporters
        DiffMergeReporter.INSTANCE, MeldMergeReporter.INSTANCE, IntelliJReporter.INSTANCE
    // end-snippet
    );
  }
}
