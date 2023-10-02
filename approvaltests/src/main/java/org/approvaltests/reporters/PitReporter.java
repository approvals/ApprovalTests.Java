package org.approvaltests.reporters;

public class PitReporter implements EnvironmentAwareReporter
{
  public static final PitReporter INSTANCE = new PitReporter();
  @Override
  public boolean report(String received, String approved)
  {
    // do nothing!
    return isWorkingInThisEnvironment(received);
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
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
