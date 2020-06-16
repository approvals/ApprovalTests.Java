package org.approvaltests.reporters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class ReporterChainingTest
{
  public static class ExceptionThrowingReporter implements EnvironmentAwareReporter
  {
    public boolean run = false;
    @Override
    public void report(String received, String approved)
    {
      run = true;
      throw new Error("Error");
    }
    @Override
    public boolean isWorkingInThisEnvironment(String forFile)
    {
      return true;
    }
  }
  public static class NonWorkingReporter implements EnvironmentAwareReporter
  {
    @Override
    public void report(String received, String approved)
    {
    }
    @Override
    public boolean isWorkingInThisEnvironment(String forFile)
    {
      return false;
    }
  }
  public static class WorkingReporter implements EnvironmentAwareReporter
  {
    public String received;
    @Override
    public void report(String received, String approved)
    {
      this.received = received;
    }
    @Override
    public boolean isWorkingInThisEnvironment(String forFile)
    {
      return true;
    }
  }
  @Test
  public void testFirstWorkingReporter() throws Exception
  {
    WorkingReporter workingReporter = new WorkingReporter();
    WorkingReporter workingReporter2 = new WorkingReporter();
    FirstWorkingReporter reporter = new FirstWorkingReporter(new NonWorkingReporter(), workingReporter,
        workingReporter2);
    reporter.report("Hello", "world");
    assertEquals("Hello", workingReporter.received);
    assertNull(workingReporter2.received);
    assertTrue(reporter.isWorkingInThisEnvironment(""));
  }
  @Test
  public void testMultiReporter()
  {
    WorkingReporter workingReporter = new WorkingReporter();
    WorkingReporter workingReporter2 = new WorkingReporter();
    MultiReporter reporter = new MultiReporter(workingReporter, workingReporter2);
    reporter.report("Hello", "world");
    assertEquals("Hello", workingReporter.received);
    assertEquals("Hello", workingReporter2.received);
  }
  @Test
  public void testMultiReporterWithExceptions()
  {
    ExceptionThrowingReporter exception1 = new ExceptionThrowingReporter();
    ExceptionThrowingReporter exception2 = new ExceptionThrowingReporter();
    MultiReporter reporter = new MultiReporter(exception1, exception2);
    try
    {
      reporter.report("Hello", "world");
    }
    catch (Throwable t)
    {
      assertTrue(exception1.run);
      assertTrue(exception2.run);
      Approvals.verify(t.getMessage());
    }
  }
}
