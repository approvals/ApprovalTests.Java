package org.approvaltests.reporters.tests;

import junit.framework.TestCase;

import org.approvaltests.ReporterFactory;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.MultiReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;

public class UseReporterTest extends TestCase
{
  @UseReporter(TortoiseTextDiffReporter.class)
  public void testUseReporter() throws Exception
  {
    assertEquals(TortoiseTextDiffReporter.class, ReporterFactory.getFromAnnotation().getClass());
  }
  @UseReporter({TortoiseTextDiffReporter.class, ClipboardReporter.class})
  public void testMultipleUseReporter() throws Exception
  {
    assertEquals(MultiReporter.class, ReporterFactory.getFromAnnotation().getClass());
  }
}
