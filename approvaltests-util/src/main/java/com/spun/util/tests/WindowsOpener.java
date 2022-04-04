package com.spun.util.tests;

import com.spun.util.SystemUtils;

public class WindowsOpener implements Opener
{
  @Override
  public boolean open(String fileName)
  {
    if (!SystemUtils.isWindowsEnvironment())
    { return false; }
    String cmd = String.format("cmd /C start \"Needed Title\" \"%s\" /B", fileName);
    Opener.execute(cmd);
    return true;
  }
}
