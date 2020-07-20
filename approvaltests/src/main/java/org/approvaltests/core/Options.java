package org.approvaltests.core;

import java.util.Optional;

import org.approvaltests.ReporterFactory;
import org.approvaltests.scrubbers.NoOpScrubber;

public class Options
{
  private Scrubber                          scrubber = NoOpScrubber.INSTANCE;
  private Optional<ApprovalFailureReporter> reporter = Optional.empty();
  public Options()
  {
  }
  public Options(Scrubber scrubber)
  {
    this.scrubber = scrubber;
  }
  public Options(ApprovalFailureReporter reporter)
  {
    this.reporter = Optional.ofNullable(reporter);
  }
  private Options(Scrubber scrubber, Optional<ApprovalFailureReporter> reporter)
  {
    this.scrubber = scrubber;
    this.reporter = reporter;
  }
  public ApprovalFailureReporter getReporter()
  {
    return this.reporter.orElse(ReporterFactory.get());
  }
  public Options withReporter(ApprovalFailureReporter reporter)
  {
    return new Options(this.scrubber, Optional.ofNullable(reporter));
  }
  public Options withScrubber(Scrubber scrubber)
  {
    return new Options(scrubber, this.reporter);
  }
  public String scrub(String input)
  {
    return scrubber.scrub(input);
  }
}
