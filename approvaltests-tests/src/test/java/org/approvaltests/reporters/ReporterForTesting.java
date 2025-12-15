package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;

public class ReporterForTesting implements ApprovalFailureReporter
{
  private final boolean works;
  public ReporterForTesting(boolean works)
  {
    this.works = works;
  }

  @Override
  public boolean report(String received, String approved)
  {
    return works;
  }

  @Override
  public String toString()
  {
    return "ReporterForTesting(" + (works ? "True" : "False") + ")";
  }
}
