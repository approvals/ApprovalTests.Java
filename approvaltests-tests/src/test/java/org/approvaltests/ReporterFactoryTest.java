package org.approvaltests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.EnvironmentAwareReporter;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import com.spun.util.ThreadUtils;

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