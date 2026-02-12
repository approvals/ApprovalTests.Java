// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters;

import org.approvaltests.reporters.windows.ReportWithTortoiseImageDiffWindows;
import org.approvaltests.reporters.windows.ReportWithTortoiseTextDiffWindows;

public class ReportWithTortoise extends FirstWorkingReporter
{
  public static final ReportWithTortoise INSTANCE = new ReportWithTortoise();
  public ReportWithTortoise()
  {
    super(ReportWithTortoiseImageDiffWindows.INSTANCE, ReportWithTortoiseTextDiffWindows.INSTANCE);
  }
}
