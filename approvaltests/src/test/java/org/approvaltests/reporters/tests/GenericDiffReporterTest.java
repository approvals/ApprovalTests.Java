package org.approvaltests.reporters.tests;

import java.io.File;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.DiffMergeReporter;
import org.approvaltests.reporters.macosx.MacDiffReporter;
import org.approvaltests.reporters.macosx.P4MergeReporter;
import org.approvaltests.reporters.macosx.TkDiffReporter;
import org.approvaltests.reporters.macosx.VisualStudioCodeReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;
import org.approvaltests.reporters.windows.WinMergeReporter;

import com.spun.util.ClassUtils;
import com.spun.util.SystemUtils;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GenericDiffReporterTest
{
  @UseReporter(DiffMergeReporter.class)
  @Test
  public void testArguementParsing() throws Exception
  {
    Approvals.verifyAll("CommandLine",
        VisualStudioCodeReporter.INSTANCE.getCommandLine("received.txt", "approved.txt"));
  }
  @UseReporter(DiffMergeReporter.class)
  @Test
  @Ignore
  public void testGetWorkingReportesForEnviroment() throws Exception
  {
    Approvals.verifyAll("reporters", MacDiffReporter.INSTANCE.getWorkingReportersForEnviroment());
  }
  @Test
  public void testFileExtensions() throws Exception
  {
    assertTrue(new GenericDiffReporter("", "").isFileExtensionHandled("a.txt"));
  }
  @Test
  public void testProgramsExist() throws Exception
  {
    assertFalse(new GenericDiffReporter("this_should_never_exist", "").isWorkingInThisEnvironment("a.txt"));
  }
  @Test
  public void testTkDiff() throws Exception
  {
    approveGenericReporter("a.txt", "b.txt", new TkDiffReporter());
  }
  @Test
  public void testTortoiseDiff() throws Exception
  {
    approveGenericReporter("a.txt", "b.txt", new TortoiseTextDiffReporter());
  }
  @Test
  public void testWinMerge() throws Exception
  {
    approveGenericReporter("a.txt", "b.txt", new WinMergeReporter());
  }
  @Test
  public void testP4Merge() throws Exception
  {
    approveGenericReporter("a.png", "b.png", new P4MergeReporter());
  }
  @Test
  public void testSpacesInFileNames() {
    GenericDiffReporter reporter = new GenericDiffReporter(null, "-left=%s -right=%s", null, null);
    String[] commandLine = reporter.getCommandLine("file with spaces", "file with spaces.approved");
    Approvals.verifyAll("arguments", commandLine);
  }
  @Test
  public void testCommandLineFileNames() throws Exception
  {
    String[] names = {"regular.txt", "with spaces.txt"};
    Boolean[] isWindows = {true, false};
    CombinationApprovals.verifyAllCombinations(this, "getFileName", names, isWindows);
  }
  public String getFileName(String name, Boolean isWindows)
  {
    return SystemUtils.convertFileForCommandLine(name, isWindows);
  }
  private void approveGenericReporter(String a, String b, GenericDiffReporter reporter) throws Exception
  {
    File directory = ClassUtils.getSourceDirectory(getClass());
    String aPath = directory.getAbsolutePath() + File.separator + a;
    String bPath = directory.getAbsolutePath() + File.separator + b;
    Approvals.verify(new QueryableDiffReporterHarness(reporter, aPath, bPath));
  }
  @Test
  public void testIsImage()
  {
    String[] files = {"a.png", "a.viz.png", "a.bitmap", "a.txt"};
    Approvals.verifyAll(files, a -> String.format("Image: %s = %s", a,
        GenericDiffReporter.isFileExtensionValid(a, GenericDiffReporter.IMAGE_FILE_EXTENSIONS)));
  }
}
