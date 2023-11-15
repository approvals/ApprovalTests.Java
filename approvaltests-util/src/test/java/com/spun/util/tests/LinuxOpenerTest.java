package com.spun.util.tests;

import com.spun.util.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinuxOpenerTest
{
  @Disabled("This test takes a lot of time to run and opens a browser window")
  @Test
  void testMultipleFilesOnLinux() throws Exception
  {
    File tempFile = File.createTempFile("test", ".html");
    FileUtils.writeFile(tempFile, "<html><body><h1>Applesauce</h1></body></html>");
    int countCorrect = 0;
    int totalRuns = 10;
    for (int i = 0; i < totalRuns; i++)
    {
      if (LinuxOpener.executeOnLinux(tempFile.getAbsolutePath(), "open"))
      {
        countCorrect++;
      }
    }
    assertEquals(totalRuns, countCorrect);
  }
}
