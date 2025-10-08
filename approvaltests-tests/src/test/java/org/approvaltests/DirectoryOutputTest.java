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
    Approvals.verifyEachFileInDirectory(directory);
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
