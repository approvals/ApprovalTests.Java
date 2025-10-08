package org.approvaltests.reporters;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EnvironmentVariableReporterTest
{
  @Test
  public void testEnvironmentVariable()
  {
    var expected = """
        DiffReporter, QuietReporter
        """;
    try
    {
      EnvironmentVariableReporter.ENVIRONMENT_VARIABLES = (s) -> "DiffReporter,QuietReporter";
      var reporter = new EnvironmentVariableReporter().getReporter();
      Approvals.verify(reporter, new Options().inline(expected));
    }
    finally
    {
      EnvironmentVariableReporter.ENVIRONMENT_VARIABLES = System::getenv;
    }
  }

  @Test
  void testValidEnvironmentalValues()
  {
    Set<String> reporters = new EnvironmentVariableReporter().getReporterMapping().keySet();
    List<String> sortedReporters = reporters.stream().sorted().collect(Collectors.toList());
    Approvals.verifyAll("", sortedReporters, x -> x);
  }
}
