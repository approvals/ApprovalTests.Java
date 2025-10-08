package org.lambda.actions;

import com.spun.util.ObjectUtils;

/**
  * Findable name for java.lang.Runnable
  **/
public interface Action0
{
  public static Action0 doNothing()
  {
    return () -> {
    };
  }

  public void call();

  public static Action0 runtime(Action0WithException exceptions)
  {
    return () -> {
      try
      {
        exceptions.call();
      }
      catch (Throwable t)
      {
        throw ObjectUtils.throwAsError(t);
      }
    };
  }
}
