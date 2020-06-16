package org.approvaltests;

import java.io.File;

import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import com.spun.util.io.FileUtils;

@UseReporter(DiffReporter.class)
public class DirectoryOutputTest
{
  @Test
  public void testAllFilesInDirectory() throws Exception
  {
    File directory = FileUtils.createTempDirectory();
    createFilesFor(directory, 3);
    Approvals.verifyEachFileInDirectory(directory);
  }
  private void createFilesFor(File directory, int numberOfFiles)
  {
    for (int i = 1; i < numberOfFiles + 1; i++)
    {
      String fileName = directory.getAbsolutePath() + File.separator + "File" + i + ".txt";
      File file = new File(fileName);
      FileUtils.writeFile(file, "Text" + i);
    }
  }
}
