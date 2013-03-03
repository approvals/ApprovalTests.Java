package com.spun.util;

import org.lambda.actions.Action0;

public class LambdaThreadLauncher implements Runnable
{
  private final Action0 function;
  private long     delay = 0;
  public LambdaThreadLauncher(Action0 function)
  {
    this.function = function;
    new Thread(this).start();
  }
  @Override
  public void run()
  {
    try
    {
      Thread.sleep(delay);
      function.call();
    }
    catch (Throwable t)
    {
      MySystem.warning("Caught throwable exception ", t);
    }
  }
}
