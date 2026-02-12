// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters;

import org.approvaltests.reporters.windows.ReportWithTortoiseGitImageDiffWindows;
import org.approvaltests.reporters.windows.ReportWithTortoiseGitTextDiffWindows;

public class ReportWithTortoiseGit extends FirstWorkingReporter
{
  public static final ReportWithTortoiseGit INSTANCE = new ReportWithTortoiseGit();
  public ReportWithTortoiseGit()
  {
    super(
        ReportWithTortoiseGitImageDiffWindows.INSTANCE,
        ReportWithTortoiseGitTextDiffWindows.INSTANCE
    );
  }
}
