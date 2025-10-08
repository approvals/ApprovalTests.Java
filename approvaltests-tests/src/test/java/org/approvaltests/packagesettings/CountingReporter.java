package org.approvaltests.packagesettings;

import org.approvaltests.core.ApprovalFailureReporter;

public class CountingReporter implements ApprovalFailureReporter
{
  public int count;
  @Override
  public boolean report(String received, String approved)
  {
    // do Nothing
    return isWorkingInThisEnvironment(received);
  }

  public boolean isWorkingInThisEnvironment(String forFile)
  {
    count++;
    return count == 11;
  }
}
