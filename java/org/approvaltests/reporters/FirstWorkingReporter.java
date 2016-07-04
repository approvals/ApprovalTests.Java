package org.approvaltests.reporters;

import java.util.List;

import org.approvaltests.core.ApprovalFailureReporter;
import org.lambda.functions.Function1;
import org.lambda.query.Query;

public class FirstWorkingReporter implements EnvironmentAwareReporter
{
  private final EnvironmentAwareReporter[] reporters;
  public FirstWorkingReporter(EnvironmentAwareReporter... reporters)
  {
    this.reporters = reporters;
  }
  public static FirstWorkingReporter combine(EnvironmentAwareReporter front, ApprovalFailureReporter last)
  {
    return new FirstWorkingReporter(front, wrap(last));
  }
  private static EnvironmentAwareReporter wrap(ApprovalFailureReporter last)
  {
    if (last instanceof EnvironmentAwareReporter) { return (EnvironmentAwareReporter) last; }
    return new AlwaysWorkingReporter(last);
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
  public EnvironmentAwareReporter[] getReporters()
  {
    return reporters;
  }
  public List<EnvironmentAwareReporter> getWorkingReportersForEnviroment()
  {
    return Query.where(reporters, new Function1<EnvironmentAwareReporter, Boolean>()
    {
      public Boolean call(EnvironmentAwareReporter r)
      {
        return r.isWorkingInThisEnvironment("a.txt");
      }
    });
  }
}
