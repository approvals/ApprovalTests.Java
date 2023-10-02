package org.approvaltests;

import com.spun.util.ThreadUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UseReporter(ClipboardReporter.class)
public class ReporterFactoryTest
{
  @Test
  public void testReportersAtClassLevel()
  {
    assertEquals(ClipboardReporter.class, ReporterFactoryHelper.getClassFor());
  }
  @Test
  @UseReporter(DiffReporter.class)
  public void oneLayerDown()
  {
    StackListings<UseReporter> listings = ReporterFactory.getAnnotationsFromStackTrace(UseReporter.class,
        ThreadUtils.getStackTrace());
    Approvals.verify(listings);
    assertEquals(DiffReporter.class, listings.getFirst().value()[0]);
  }
  @Test
  public void testPrintUseReporterInJava11()
  {
    String text = StackListings.printUserReporter(
        "@org.approvaltests.reporters.UseReporter(value={org.approvaltests.reporters.DiffReporter.class})",
        new Class[0]);
    assertEquals("@org.approvaltests.reporters.UseReporter(value=[])", text);
  }
  @Test
  public void testPrintUseReporterInJava16()
  {
    String text = StackListings.printUserReporter(
        "@org.approvaltests.reporters.UseReporter({org.approvaltests.reporters.DiffReporter.class})",
        new Class[0]);
    assertEquals("@org.approvaltests.reporters.UseReporter(value=[])", text);
  }
}

class ReporterFactoryHelper
{
  public static Class<? extends ApprovalFailureReporter> getClassFor()
  {
    FirstWorkingReporter reporter = (FirstWorkingReporter) ReporterFactory.get();
    ApprovalFailureReporter[] working = reporter.getReporters();
    return working[1].getClass();
  }
}
