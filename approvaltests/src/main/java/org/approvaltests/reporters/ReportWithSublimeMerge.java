// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters;

import org.approvaltests.reporters.linux.ReportWithSublimeMergeLinux;
import org.approvaltests.reporters.macosx.ReportWithSublimeMergeMac;
import org.approvaltests.reporters.windows.ReportWithSublimeMergeWindows;

public class ReportWithSublimeMerge extends FirstWorkingReporter
{
  public static final ReportWithSublimeMerge INSTANCE = new ReportWithSublimeMerge();
  public ReportWithSublimeMerge()
  {
    super(ReportWithSublimeMergeMac.INSTANCE, ReportWithSublimeMergeWindows.INSTANCE,
        ReportWithSublimeMergeLinux.INSTANCE);
  }
}
