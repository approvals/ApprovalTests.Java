package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.JunitReporter;
import org.approvaltests.reporters.MultiReporter;

public class ReportOnCyberDojo extends MultiReporter
{
  public static final ReportOnCyberDojo INSTANCE = new ReportOnCyberDojo();
  public ReportOnCyberDojo()
  {
    super(ReportByCreatingDiffFile.INSTANCE, JunitReporter.INSTANCE);
  }
}
