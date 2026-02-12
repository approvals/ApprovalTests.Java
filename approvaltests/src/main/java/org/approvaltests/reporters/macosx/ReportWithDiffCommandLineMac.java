// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class ReportWithDiffCommandLineMac extends GenericDiffReporter
{
  public static final ReportWithDiffCommandLineMac INSTANCE = new ReportWithDiffCommandLineMac();
  public ReportWithDiffCommandLineMac()
  {
    super(Mac.DIFF_COMMAND_LINE);
  }
}
