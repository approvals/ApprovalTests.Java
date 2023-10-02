package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
  public boolean report(String received, String approved)
  {
    boolean didAnyReporterWork = false;
    ArrayList<Throwable> exceptions = new ArrayList<Throwable>();
    for (ApprovalFailureReporter reporter : reporters)
    {
      try
      {
        didAnyReporterWork |= reporter.report(received, approved);
      }
      catch (Throwable t)
      {
        exceptions.add(t);
      }
    }
    MultipleExceptions.rethrowExceptions(exceptions);
    return didAnyReporterWork;
  }
  public ApprovalFailureReporter[] getReporters()
  {
    return reporters.toArray(new ApprovalFailureReporter[0]);
  }
}
