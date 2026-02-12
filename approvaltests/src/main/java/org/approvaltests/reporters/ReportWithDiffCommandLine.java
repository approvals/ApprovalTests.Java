// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters;

import org.approvaltests.reporters.linux.ReportWithDiffCommandLineLinux;
import org.approvaltests.reporters.macosx.ReportWithDiffCommandLineMac;

public class ReportWithDiffCommandLine extends FirstWorkingReporter
{
  public static final ReportWithDiffCommandLine INSTANCE = new ReportWithDiffCommandLine();
  public ReportWithDiffCommandLine()
  {
    super(
        ReportWithDiffCommandLineLinux.INSTANCE,
        ReportWithDiffCommandLineMac.INSTANCE
    );
  }
}
