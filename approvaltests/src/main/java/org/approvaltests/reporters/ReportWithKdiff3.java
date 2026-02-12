// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters;

import org.approvaltests.reporters.linux.ReportWithKdiff3Linux;
import org.approvaltests.reporters.macosx.ReportWithKdiff3Mac;
import org.approvaltests.reporters.windows.ReportWithKdiff3Windows;

public class ReportWithKdiff3 extends FirstWorkingReporter
{
  public static final ReportWithKdiff3 INSTANCE = new ReportWithKdiff3();
  public ReportWithKdiff3()
  {
    super(ReportWithKdiff3Mac.INSTANCE, ReportWithKdiff3Windows.INSTANCE, ReportWithKdiff3Linux.INSTANCE);
  }
}
