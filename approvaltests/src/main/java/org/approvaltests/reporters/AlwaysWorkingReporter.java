package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;

public class AlwaysWorkingReporter implements EnvironmentAwareReporter
{
  private ApprovalFailureReporter wrapped;
  public AlwaysWorkingReporter(ApprovalFailureReporter wrapped)
  {
    this.wrapped = wrapped;
  }
  @Override
  public boolean report(String received, String approved)
  {
    getWrapped().report(received, approved);
    return isWorkingInThisEnvironment(received);
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    return true;
  }
  public ApprovalFailureReporter getWrapped()
  {
    return wrapped;
  }
}
