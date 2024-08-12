package org.approvaltests.reporters;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}
