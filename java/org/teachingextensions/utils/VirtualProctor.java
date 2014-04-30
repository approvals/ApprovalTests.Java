package org.teachingextensions.utils;

import java.io.File;

import com.spun.util.StringUtils;
import com.spun.util.io.FileUtils;

public class VirtualProctor
{
  public static class internals
  {
    private static final String FILE_NAME = ".virtual_proctor.txt";
    public static String getName()
    {
      String name = getCustomName();
      if (name == null)
      {
        name = getComputerName();
      }
      if (name == null)
      {
        name = getUserName();
      }
      return name;
    }
    private static String getCustomName()
    {
      File file = new File(FILE_NAME);
      if (file.exists()) { return FileUtils.readFileWithSuppressedExceptions(file).trim(); }
      return null;
    }
    public static String getComputerName()
    {
      return System.getenv("COMPUTERNAME");
    }
    public static String getUserName()
    {
      return System.getenv("USER");
    }
    public static void resetName()
    {
      File file = new File(FILE_NAME);
      if (file.exists())
      {
        file.delete();
      }
    }
    public static void setName(String name)
    {
      if (StringUtils.isEmpty(name)) { return; }
      FileUtils.writeFileQuietly(new File(FILE_NAME), name);
    }
  }
  public static void setName(String name)
  {
    internals.setName(name);
  }
}
