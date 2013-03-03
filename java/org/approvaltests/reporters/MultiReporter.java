package org.approvaltests.reporters;

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
  public void report(String received, String approved) throws Exception
  {
    for (ApprovalFailureReporter reporter : reporters)
    {
      reporter.report(received, approved);
    }
  }
}
