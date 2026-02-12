// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters;

import org.approvaltests.reporters.linux.ReportWithDiffMergeLinux;
import org.approvaltests.reporters.macosx.ReportWithDiffMergeMac;

public class ReportWithDiffMerge extends FirstWorkingReporter
{
  public static final ReportWithDiffMerge INSTANCE = new ReportWithDiffMerge();
  public ReportWithDiffMerge()
  {
    super(ReportWithDiffMergeMac.INSTANCE, ReportWithDiffMergeLinux.INSTANCE);
  }
}
