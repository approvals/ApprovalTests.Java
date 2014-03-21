package org.teachingextensions.setup;

import com.spun.util.SystemUtils;

public class ThumbDriveInstaller
{
  public static void main(String[] args) throws Exception
  {
    installForThumbDrive();
  }
  private static void installForThumbDrive() throws Exception
  {
    if (SystemUtils.isWindowsEnviroment())
    {
      new WindowsThumbDriveInstaller().install();
    }
    else
    {
      new MacThumbDriveInstaller().install();
    }
  }
}
