// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class ReportWithTkDiffMac extends GenericDiffReporter
{
  public static final ReportWithTkDiffMac INSTANCE = new ReportWithTkDiffMac();
  public ReportWithTkDiffMac()
  {
    super(Mac.TK_DIFF);
  }
}
