package org.approvaltests.tests;

import org.approvaltests.Approvals;
import org.approvaltests.ReporterFactory;
import org.approvaltests.StackListings;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.EnvironmentAwareReporter;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.PitReporter;
import org.approvaltests.reporters.UseReporter;

import com.spun.util.ThreadUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@UseReporter(ClipboardReporter.class)
public class ReporterFactoryTest
{
  @Test
  public void testReportersAtClassLevel() throws Exception
  {
    assertEquals(ClipboardReporter.class, ReporterFactoryHelper.getClassFor());
  }
  @Test
  @UseReporter(PitReporter.class)
  public void testReportersAtMethodLevel() throws Exception
  {
    oneLayerDown();
    assertEquals(PitReporter.class, ReporterFactoryHelper.getClassFor());
  }
  @UseReporter(DiffReporter.class)
  public void oneLayerDown() throws Exception
  {
    StackListings<UseReporter> listings = ReporterFactory.getAnnotationsFromStackTrace(UseReporter.class,
        ThreadUtils.getStackTrace());
    Approvals.verify(listings);
    assertEquals(DiffReporter.class, listings.getFirst().value()[0]);
  }
}

class ReporterFactoryHelper
{
  public static Class<? extends EnvironmentAwareReporter> getClassFor()
  {
    FirstWorkingReporter reporter = (FirstWorkingReporter) ReporterFactory.get();
    EnvironmentAwareReporter[] working = reporter.getReporters();
    return working[1].getClass();
  }
}