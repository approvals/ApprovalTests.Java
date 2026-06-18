package org.approvaltests.reporters;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

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
      EnvironmentVariableReporter.GET_ENVIRONMENT_VARIABLE = (
          s) -> "org.approvaltests.reporters.DiffReporter,org.approvaltests.reporters.QuietReporter";
      var reporter = new EnvironmentVariableReporter().getReporter();
      Approvals.verify(reporter, new Options().inline(expected));
    }
    finally
    {
      EnvironmentVariableReporter.GET_ENVIRONMENT_VARIABLE = System::getenv;
    }
  }
}
