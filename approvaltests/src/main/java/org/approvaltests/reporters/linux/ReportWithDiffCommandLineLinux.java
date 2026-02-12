// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.DiffPrograms.Linux;
import org.approvaltests.reporters.GenericDiffReporter;

public class ReportWithDiffCommandLineLinux extends GenericDiffReporter
{
  public static final ReportWithDiffCommandLineLinux INSTANCE = new ReportWithDiffCommandLineLinux();
  public ReportWithDiffCommandLineLinux()
  {
    super(Linux.DIFF_COMMAND_LINE);
  }
}
