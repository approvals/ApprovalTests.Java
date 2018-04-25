package org.approvaltests.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;

import com.spun.util.io.FileUtils;

@UseReporter(DiffReporter.class)
public class DirectoryOutputTest extends TestCase
{
  public void testAllFilesInDirectory() throws Exception
  {
    File directory = FileUtils.createTempDirectory();
    createFilesFor(directory, 3);
    Approvals.verifyEachFileInDirectory(directory);
  }
  private void createFilesFor(File directory, int numberOfFiles) throws IOException
  {
    for (int i = 1; i < numberOfFiles + 1; i++)
    {
      String fileName = directory.getAbsolutePath() + File.separator + "File" + i + ".txt";
      File file = new File(fileName);
      FileUtils.writeFile(file, "Text" + i);
    }
  }
}
