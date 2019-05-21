package org.approvaltests.packagesettings.tests;

import org.approvaltests.reporters.EnvironmentAwareReporter;

public class CountingReporter implements EnvironmentAwareReporter
{
  public int count;
  @Override
  public void report(String received, String approved) throws Exception
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