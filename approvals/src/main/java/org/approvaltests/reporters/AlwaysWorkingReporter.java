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
  public void report(String received, String approved) throws Exception
  {
    getWrapped().report(received, approved);
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
