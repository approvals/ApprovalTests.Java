package com.spun.util.tests;

import com.spun.util.ObjectUtils;

public interface Opener
{
  public boolean open(String fileName);

  public static void execute(String cmd)
  {
    try
    {
      Runtime.getRuntime().exec(cmd);
      Thread.sleep(2000);
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
