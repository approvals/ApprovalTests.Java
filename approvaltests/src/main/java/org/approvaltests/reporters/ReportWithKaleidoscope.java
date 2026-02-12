// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters;

import org.approvaltests.reporters.macosx.ReportWithKaleidoscope3Mac;
import org.approvaltests.reporters.macosx.ReportWithKaleidoscopeMac;

public class ReportWithKaleidoscope extends FirstWorkingReporter
{
  public static final ReportWithKaleidoscope INSTANCE = new ReportWithKaleidoscope();
  public ReportWithKaleidoscope()
  {
    super(
        ReportWithKaleidoscopeMac.INSTANCE,
        ReportWithKaleidoscope3Mac.INSTANCE
    );
  }
}
