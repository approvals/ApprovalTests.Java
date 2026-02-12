// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class ReportWithCodeCompareWindows extends GenericDiffReporter
{
  public static final ReportWithCodeCompareWindows INSTANCE = new ReportWithCodeCompareWindows();
  public ReportWithCodeCompareWindows()
  {
    super(Windows.CODE_COMPARE);
  }
}
