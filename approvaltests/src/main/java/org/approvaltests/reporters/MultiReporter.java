package org.approvaltests.reporters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.approvaltests.core.ApprovalFailureReporter;

public class MultiReporter implements ApprovalFailureReporter
{
  private final Collection<ApprovalFailureReporter> reporters;
  public MultiReporter(Collection<ApprovalFailureReporter> reporters)
  {
    this.reporters = reporters;
  }
  public MultiReporter(ApprovalFailureReporter... reporters)
  {
    this.reporters = Arrays.asList(reporters);
  }
  @Override
  public void report(String received, String approved)
  {
    ArrayList<Throwable> exceptions = new ArrayList<Throwable>();
    for (ApprovalFailureReporter reporter : reporters)
    {
      try
      {
        reporter.report(received, approved);
      }
      catch (Throwable t)
      {
        exceptions.add(t);
      }
    }
    MultipleExceptions.rethrowExceptions(exceptions);
  }
  public ApprovalFailureReporter[] getReporters()
  {
    return reporters.toArray(new ApprovalFailureReporter[0]);
  }
}
