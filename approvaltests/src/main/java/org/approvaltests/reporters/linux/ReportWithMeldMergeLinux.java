// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.DiffPrograms.Linux;
import org.approvaltests.reporters.GenericDiffReporter;

public class ReportWithMeldMergeLinux extends GenericDiffReporter
{
  public static final ReportWithMeldMergeLinux INSTANCE = new ReportWithMeldMergeLinux();
  public ReportWithMeldMergeLinux()
  {
    super(Linux.MELD_MERGE);
  }
}
