// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.DiffPrograms.Linux;
import org.approvaltests.reporters.GenericDiffReporter;

public class ReportWithKdiff3Linux extends GenericDiffReporter
{
  public static final ReportWithKdiff3Linux INSTANCE = new ReportWithKdiff3Linux();
  public ReportWithKdiff3Linux()
  {
    super(Linux.KDIFF3);
  }
}
