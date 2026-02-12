// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters;

import org.approvaltests.reporters.macosx.ReportWithAraxisMergeMac;
import org.approvaltests.reporters.windows.ReportWithAraxisMergeWindows;

public class ReportWithAraxisMerge extends FirstWorkingReporter
{
  public static final ReportWithAraxisMerge INSTANCE = new ReportWithAraxisMerge();
  public ReportWithAraxisMerge()
  {
    super(ReportWithAraxisMergeMac.INSTANCE, ReportWithAraxisMergeWindows.INSTANCE);
  }
}
