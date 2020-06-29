package com.spun.util;

import org.lambda.actions.Action0;

import com.spun.util.logger.SimpleLogger;

public class LambdaThreadLauncher implements Runnable
{
  private final Action0 function;
  private long          delay = 0;
  private Thread        thread;
  public LambdaThreadLauncher(Action0 function)
  {
    this(function, 0);
  }
  public LambdaThreadLauncher(Action0 function, long delay)
  {
    this.delay = delay;
    this.function = function;
    thread = new Thread(this);
    thread.start();
  }
  public Thread getThread()
  {
    return thread;
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
      SimpleLogger.warning("Caught throwable exception ", t);
    }
  }
}
