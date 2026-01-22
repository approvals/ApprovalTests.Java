package org.approvaltests;

import com.spun.util.io.FileUtils;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.io.File;

@UseReporter(DiffReporter.class)
public class DirectoryOutputTest
{
  @Test
  public void testAllFilesInDirectory()
  {
    File directory = FileUtils.createTempDirectory();
    createFilesFor(directory, 3);
    // begin-snippet: verify_multiple_files
    Approvals.verifyEachFileInDirectory(directory);
    // end-snippet
  }

  @Test
  public void testAllFilesInDirectoryWithFilter()
  {
    File directory = FileUtils.createTempDirectory();
    createFilesFor(directory, 3);
    createIgnoredFile(directory);
    // begin-snippet: verify_multiple_files_with_filter
    Approvals.verifyEachFileInDirectory(directory, f -> !f.getName().endsWith("ignore.txt"));
    // end-snippet
  }

  private static void createIgnoredFile(File directory) {
    File file = new File(directory, "ignore.txt");
    FileUtils.writeFile(file, "ignore.txt");
  }

  private void createFilesFor(File directory, int numberOfFiles)
  {
    for (int i = 1; i < numberOfFiles + 1; i++)
    {
      File file = new File(directory, "File" + i + ".txt");
      FileUtils.writeFile(file, "Text" + i);
    }
  }
}
