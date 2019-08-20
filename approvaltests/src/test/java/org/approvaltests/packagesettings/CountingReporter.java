package org.approvaltests.packagesettings;

import org.approvaltests.reporters.EnvironmentAwareReporter;

public class CountingReporter implements EnvironmentAwareReporter
{
  public int count;
  @Override
  public void report(String received, String approved)
  {
    // do Nothing
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    count++;
    return count == 11;
  }
}