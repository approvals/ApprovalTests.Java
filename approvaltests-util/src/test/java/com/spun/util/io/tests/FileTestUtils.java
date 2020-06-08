package com.spun.util.io.tests;

import java.io.File;
import junit.framework.TestCase;

public class FileTestUtils
{
  
  public static void assertFileRecentlyCreated(String fileName, boolean delete)
  {
    File file = new File(fileName);
    try
    {
      TestCase.assertTrue("File exists", file.exists());
      TestCase.assertTrue("File recently created", (System.currentTimeMillis() - file.lastModified()) < 5000);
    }
    finally
    {
      if (delete && file.exists())
      {
        file.delete();
      }
    }
  }
  
  
}
