// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class ReportWithP4mergeMac extends GenericDiffReporter
{
  public static final ReportWithP4mergeMac INSTANCE = new ReportWithP4mergeMac();
  public ReportWithP4mergeMac()
  {
    super(Mac.P4MERGE);
  }
}
