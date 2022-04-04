package com.spun.util.tests;

import static com.spun.util.tests.LinuxOpener.executeOnLinux;

public class MacOpener implements Opener
{
  @Override
  public boolean open(String fileName)
  {
    return executeOnLinux(fileName, "open");
  }
}
