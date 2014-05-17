package org.approvaltests.reporters.tests;

import java.io.File;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.P4MergeReporter;
import org.approvaltests.reporters.macosx.TkDiffReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;
import org.approvaltests.reporters.windows.WinMergeReporter;
import org.approvaltests.strings.Printer;

import com.spun.util.ClassUtils;

@UseReporter(P4MergeReporter.class)
public class GenericDiffReporterTest extends TestCase
{
  public void testFileExtensions() throws Exception
  {
    assertTrue(new GenericDiffReporter("", "").isFileExtensionHandled("a.txt"));
  }
  public void testProgramsExist() throws Exception
  {
    assertFalse(new GenericDiffReporter("this_should_never_exist", "").isWorkingInThisEnvironment("a.txt"));
  }
  public void testTkDiff() throws Exception
  {
    approveGenericReporter("a.txt", "b.txt", new TkDiffReporter());
  }
  public void testTortoiseDiff() throws Exception
  {
    approveGenericReporter("a.txt", "b.txt", new TortoiseTextDiffReporter());
  }
  public void testWinMerge() throws Exception
  {
    approveGenericReporter("a.txt", "b.txt", new WinMergeReporter());
  }
  public void testP4Merge() throws Exception
  {
    approveGenericReporter("a.png", "b.png", new P4MergeReporter());
  }
  private void approveGenericReporter(String a, String b, GenericDiffReporter reporter) throws Exception
  {
    File directory = ClassUtils.getSourceDirectory(getClass());
    String aPath = directory.getAbsolutePath() + File.separator + a;
    String bPath = directory.getAbsolutePath() + File.separator + b;
    Approvals.verify(new QueryableDiffReporterHarness(reporter, aPath, bPath));
  }
  public void testIsImage()
  {
    String[] files = {"a.png", "a.viz.png", "a.bitmap", "a.txt"};
    Approvals.verifyAll(files, new Printer<String>(".")
    {
      {
        format("Image: %s = %s", a,
            GenericDiffReporter.isFileExtensionValid(a, GenericDiffReporter.IMAGE_FILE_EXTENSIONS));
      }
    });
  }
}
