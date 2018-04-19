package org.lambda.actions;

import com.spun.util.ObjectUtils;

public interface Action0
{
  public void call();
  public static Action0 runtime(Action0WithExceptions exceptions)
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
  public static interface Action0WithExceptions
  {
    public void call() throws Throwable;
  }
}
