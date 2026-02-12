// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters;

import org.approvaltests.reporters.macosx.ReportWithBeyondCompareMac;
import org.approvaltests.reporters.windows.ReportWithBeyondCompare3Windows;
import org.approvaltests.reporters.windows.ReportWithBeyondCompare4Windows;
import org.approvaltests.reporters.windows.ReportWithBeyondCompare5Windows;

public class ReportWithBeyondCompare extends FirstWorkingReporter
{
  public static final ReportWithBeyondCompare INSTANCE = new ReportWithBeyondCompare();
  public ReportWithBeyondCompare()
  {
    super(
        ReportWithBeyondCompareMac.INSTANCE,
        ReportWithBeyondCompare3Windows.INSTANCE,
        ReportWithBeyondCompare4Windows.INSTANCE,
        ReportWithBeyondCompare5Windows.INSTANCE
    );
  }
}
