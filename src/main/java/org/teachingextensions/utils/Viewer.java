package org.teachingextensions.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.spun.util.ObjectUtils;
import com.spun.util.tests.TestUtils;

public class Viewer
{
  public static void displayRtfFile(String text)
  {
    try
    {
      File file;
      file = File.createTempFile("currentStory", ".rtf");
      FileWriter f = new FileWriter(file);
      f.write(text);
      f.close();
      TestUtils.displayFile(file.getPath());
    }
    catch (IOException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
