package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;

public class ReportNothing implements ApprovalFailureReporter
{
  public static final ReportNothing INSTANCE = new ReportNothing();
  @Override
  public boolean report(String received, String approved)
  {
    return true;
  }
}
