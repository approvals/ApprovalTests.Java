package com.spun.util.tests;

import com.spun.util.io.FileUtils;

public class LinuxOpener implements Opener
{
  @Override
  public boolean open(String fileName)
  {
    return executeOnLinux(fileName, "xdg-open");
  }
  public static boolean executeOnLinux(String fileName, String program)
  {
    if (which(program) == null)
    { return false; }
    String cmd = String.format(program + " %s", fileName);
    Opener.execute(cmd);
    return true;
  }
  public static String which(String command)
  {
    try
    {
      String command1 = "which " + command;
      Process p = Runtime.getRuntime().exec(command1);
      p.waitFor();
      String output = FileUtils.readStream(p.getInputStream()).trim();
      int exitValue = p.exitValue();
      return exitValue == 0 ? output : null;
    }
    catch (Exception e)
    {
      return null;
    }
  }
}
