package org.approvaltests.reporters;

public class FirstWorkingReporter implements EnvironmentAwareReporter
{
  private final EnvironmentAwareReporter[] reporters;
  public FirstWorkingReporter(EnvironmentAwareReporter... reporters)
  {
    this.reporters = reporters;
  }
  @Override
  public void report(String received, String approved) throws Exception
  {
    for (EnvironmentAwareReporter reporter : reporters)
    {
      if (reporter.isWorkingInThisEnvironment(received))
      {
        reporter.report(received, approved);
        return;
      }
    }
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    for (EnvironmentAwareReporter reporter : reporters)
    {
      if (reporter.isWorkingInThisEnvironment(forFile)) { return true; }
    }
    return false;
  }
}
