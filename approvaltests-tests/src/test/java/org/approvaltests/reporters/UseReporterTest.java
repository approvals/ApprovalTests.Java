package org.approvaltests.reporters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.approvaltests.ReporterFactory;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;
import org.junit.jupiter.api.Test;

import com.spun.util.ThreadUtils;

public class UseReporterTest
{
  @UseReporter(TortoiseTextDiffReporter.class)
  @Test
  public void testUseReporter()
  {
    assertEquals(TortoiseTextDiffReporter.class,
        ReporterFactory.getFromAnnotation(ThreadUtils.getStackTrace()).getClass());
  }
  @UseReporter({TortoiseTextDiffReporter.class, ClipboardReporter.class})
  @Test
  public void testMultipleUseReporter()
  {
    assertEquals(MultiReporter.class, ReporterFactory.getFromAnnotation(ThreadUtils.getStackTrace()).getClass());
  }
}
