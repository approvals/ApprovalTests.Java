package org.approvaltests.machine_specific_tests.reporters;

import com.spun.util.ClassUtils;
import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.machine_specific_tests.MachineSpecificTest;
import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.QueryableDiffReporterHarness;
import org.approvaltests.reporters.windows.ReportWithTortoiseTextDiffWindows;
import org.approvaltests.reporters.windows.ReportWithWinMergeReporterWindows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

public class GenericDiffReporterTest extends MachineSpecificTest
{
  @Disabled("requires windows and TortoiseDiff installed")
  @Test
  public void testTortoiseDiff()
  {
    approveGenericReporter("a12.txt", "b12.txt", new ReportWithTortoiseTextDiffWindows());
  }

  @Disabled("requires windows and WinMerge installed")
  @Test
  public void testWinMerge()
  {
    approveGenericReporter("a3.txt", "b13.txt", new ReportWithWinMergeReporterWindows());
  }

  private void approveGenericReporter(String a, String b, GenericDiffReporter reporter)
  {
    File directory = ClassUtils.getSourceDirectory(getClass());
    String aPath = FileUtils.getResolvedPath(new File(directory, a));
    String bPath = FileUtils.getResolvedPath(new File(directory, b));
    Approvals.verify(new QueryableDiffReporterHarness(reporter, aPath, bPath));
  }
}
