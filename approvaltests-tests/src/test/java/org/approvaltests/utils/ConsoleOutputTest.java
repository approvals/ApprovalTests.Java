package org.approvaltests.utils;

import org.approvaltests.core.Options;
import org.approvaltests.reporters.AutoApproveReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

@UseReporter(AutoApproveReporter.class)
public class ConsoleOutputTest
{
  @Test
  void testVerifyOutput()
  {
    var expected = """
        Hello, World!
        No newline
        """;
    try (ConsoleOutput console = new ConsoleOutput())
    {
      System.out.println("Hello, World!");
      System.out.print("No ");
      System.out.print("newline");
      console.verifyOutput(new Options().inline(expected));
    }
  }

  @Test
  void testVerifyError()
  {
    var expected = """
        Error message
        No newline
        """;
    try (ConsoleOutput console = new ConsoleOutput())
    {
      System.err.println("Error message");
      System.err.print("No ");
      System.err.print("newline");
      console.verifyError(new Options().inline(expected));
    }
  }

  @Test
  void testVerifyAll()
  {
    var expected = """
        Output:
        Standard output

        Error:
        Error output
        """;
    try (ConsoleOutput console = new ConsoleOutput())
    {
      System.out.println("Standard output");
      System.err.println("Error output");
      console.verifyAll(new Options().inline(expected));
    }
  }
}
