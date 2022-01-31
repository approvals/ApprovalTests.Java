package com.spun.util.io;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileTestUtils
{
  public static void assertFileRecentlyCreated(String fileName, boolean delete)
  {
    File file = new File(fileName);
    try
    {
      assertTrue(file.exists(), "File exists");
      assertTrue((System.currentTimeMillis() - file.lastModified()) < 5000, "File recently created");
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
