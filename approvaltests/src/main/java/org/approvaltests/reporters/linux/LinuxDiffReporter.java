package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;

public class LinuxDiffReporter extends FirstWorkingReporter
{
  public static final LinuxDiffReporter INSTANCE = new LinuxDiffReporter();
  public LinuxDiffReporter()
  {
    super(
    // @formatter:off
        // begin-snippet: linux_diff_reporters
        DiffMergeLinuxReporter.INSTANCE,
        MeldMergeReporter.INSTANCE,
        IntelliJReporter.INSTANCE,
        KDiff3LinuxReporter.INSTANCE
        // end-snippet
        // @formatter:on
    );
  }
}
