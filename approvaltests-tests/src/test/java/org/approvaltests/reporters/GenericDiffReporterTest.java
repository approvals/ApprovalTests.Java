package org.approvaltests.reporters;

import com.spun.util.ClassUtils;
import com.spun.util.SystemUtils;
import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.macosx.P4MergeReporter;
import org.approvaltests.reporters.macosx.TkDiffReporter;
import org.approvaltests.reporters.macosx.VisualStudioCodeReporter;
import org.junit.jupiter.api.Test;
import com.spun.util.logger.SimpleLogger;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenericDiffReporterTest
{
  // begin-snippet: use_reporter_single
  @UseReporter(DiffMergeReporter.class)
  // end-snippet
  @Test
  public void testArgumentParsing()
  {
    Approvals.verifyAll("CommandLine",
        VisualStudioCodeReporter.INSTANCE.getCommandLine("received.txt", "approved.txt"));
  }
  @Test
  public void testFileExtensions()
  {
    assertTrue(new GenericDiffReporter("", "").isFileExtensionHandled("a5.txt"));
  }
  @Test
  public void testProgramsExist()
  {
    assertFalse(new GenericDiffReporter("this_should_never_exist", "").isWorkingInThisEnvironment("a6.txt"));
  }
  @Test
  public void testTkDiff()
  {
    var expected = """
        /Applications/TkDiff.app/Contents/MacOS/tkdiff %s %s
        """;
    approveGenericReporter("a1.txt", "b1.txt", new TkDiffReporter(), expected);
  }
  @Test
  public void testP4Merge()
  {
    var expected = """
        /Applications/p4merge.app/Contents/MacOS/p4merge %s %s
        """;
    approveGenericReporter("a1.png", "b1.png", new P4MergeReporter(), expected);
  }
  @Test
  public void testSpacesInFileNames()
  {
    GenericDiffReporter reporter = new GenericDiffReporter(null, "-left=%s -right=%s", null, null);
    String[] commandLine = reporter.getCommandLine("file with spaces", "file with spaces.approved");
    Approvals.verifyAll("arguments", commandLine);
  }
  @Test
  public void testCommandLineFileNames()
  {
    String[] names = {"regular.txt", "with spaces.txt"};
    Boolean[] isWindows = {true, false};
    CombinationApprovals.verifyAllCombinations(this::getFileName, names, isWindows);
  }
  public String getFileName(String name, Boolean isWindows)
  {
    return SystemUtils.convertFileForCommandLine(name, isWindows);
  }
  private void approveGenericReporter(String a, String b, GenericDiffReporter reporter, String expected)
  {
    File directory = ClassUtils.getSourceDirectory(getClass());
    String aPath = FileUtils.getResolvedPath(new File(directory, a));
    String bPath = FileUtils.getResolvedPath(new File(directory, b));
    Approvals.verify(new QueryableDiffReporterHarness(reporter, aPath, bPath), new Options().inline(expected));
  }
  @Test
  public void testIsImage()
  {
    String[] files = {"a.png", "a.viz.png", "a.bitmap", "a.txt"};
    Approvals.verifyAll(files, a -> String.format("Image: %s = %s", a,
        GenericDiffReporter.isFileExtensionValid(a, GenericDiffReporter.IMAGE_FILE_EXTENSIONS)));
  }
  @Test
  void testRunningNonExistantFile()
  {
    try (var l = SimpleLogger.quiet())
    {
      GenericDiffReporter genericDiffReporter = new GenericDiffReporter("not-a-diff-program.exe");
      assertFalse(genericDiffReporter.launch("received.txt", "approved.txt"));
    }
  }
  @Test
  void testProgramDidNotWork()
  {
    GenericDiffReporter genericDiffReporter = new GenericDiffReporter("false");
    assertFalse(genericDiffReporter.launch("received.txt", "approved.txt"));
  }
}
