package org.approvaltests.reporters;

import com.spun.util.ThreadUtils;
import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.ReporterFactory;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
  @UseReporter(TestReporter.class)
  @Test
  void testUseReporterIsCalledWhenMethodIsNotPublic()
  {
    try
    {
      Approvals.verify("hello");
    }
    catch (Throwable e)
    {
      assertEquals("hello\n", TestReporter.getLast());
    }
  }
  public static class TestReporter implements ApprovalFailureReporter
  {
    static String last;
    public static String getLast()
    {
      return last;
    }
    @Override
    public boolean report(String received, String approved)
    {
      last = FileUtils.readFile(received);
      return true;
    }
  }
}
