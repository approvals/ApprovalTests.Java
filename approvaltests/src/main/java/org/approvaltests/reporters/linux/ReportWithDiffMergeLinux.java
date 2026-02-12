// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.DiffPrograms.Linux;
import org.approvaltests.reporters.GenericDiffReporter;

public class ReportWithDiffMergeLinux extends GenericDiffReporter
{
  public static final ReportWithDiffMergeLinux INSTANCE = new ReportWithDiffMergeLinux();
  public ReportWithDiffMergeLinux()
  {
    super(Linux.DIFF_MERGE);
  }
}
