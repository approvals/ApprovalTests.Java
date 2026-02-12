// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class ReportWithKdiff3Windows extends GenericDiffReporter
{
  public static final ReportWithKdiff3Windows INSTANCE = new ReportWithKdiff3Windows();
  public ReportWithKdiff3Windows()
  {
    super(Windows.KDIFF3);
  }
}
