// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class ReportWithKdiff3Mac extends GenericDiffReporter
{
  public static final ReportWithKdiff3Mac INSTANCE = new ReportWithKdiff3Mac();
  public ReportWithKdiff3Mac()
  {
    super(Mac.KDIFF3);
  }
}
