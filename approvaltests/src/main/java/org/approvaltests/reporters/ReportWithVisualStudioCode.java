// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters;

import org.approvaltests.reporters.macosx.ReportWithVisualStudioCodeMac;
import org.approvaltests.reporters.windows.ReportWithVisualStudioCodeWindows;

public class ReportWithVisualStudioCode extends FirstWorkingReporter
{
  public static final ReportWithVisualStudioCode INSTANCE = new ReportWithVisualStudioCode();
  public ReportWithVisualStudioCode()
  {
    super(
        ReportWithVisualStudioCodeMac.INSTANCE,
        ReportWithVisualStudioCodeWindows.INSTANCE
    );
  }
}
