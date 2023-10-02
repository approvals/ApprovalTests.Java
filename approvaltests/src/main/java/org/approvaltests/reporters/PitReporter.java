package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;

public class PitReporter implements ApprovalFailureReporter
{
  public static final PitReporter INSTANCE = new PitReporter();
  @Override
  public boolean report(String received, String approved)
  {
    // do nothing!
    return isWorkingInThisEnvironment(received);
  }
  private boolean isWorkingInThisEnvironment(String forFile)
  {
    try
    {
      Class<?> exists = Class.forName("org.pitest.testapi.TestUnit");
      return exists != null;
    }
    catch (ClassNotFoundException e)
    {
      return false;
    }
  }
}
