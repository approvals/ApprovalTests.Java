package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;

public class FirstWorkingReporter implements ApprovalFailureReporter
{
  private final ApprovalFailureReporter[] reporters;
  public FirstWorkingReporter(ApprovalFailureReporter... reporters)
  {
    this.reporters = reporters;
  }
  public static FirstWorkingReporter combine(ApprovalFailureReporter front, ApprovalFailureReporter last)
  {
    return new FirstWorkingReporter(front, last);
  }
  @Override
  public boolean report(String received, String approved)
  {
    for (ApprovalFailureReporter reporter : reporters)
    {
      if (reporter.report(received, approved))
      { return true; }
    }
    return false;
  }
  public ApprovalFailureReporter[] getReporters()
  {
    return reporters;
  }
  @Override
  public String toString()
  {
    return getClass().getName();
  }
}
