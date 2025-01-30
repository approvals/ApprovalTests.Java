package org.lambda.utils;

import org.lambda.actions.Action0;
import org.lambda.functions.Function0;

import java.util.*;

public class Once
{
  private static final Set<Class>         actions   = Collections.synchronizedSet(new HashSet<>());
  private static final Map<Class, Object> functions = Collections.synchronizedMap(new HashMap<>());
  public static void run(Action0 runnable)
  {
    if (!actions.contains(runnable.getClass()))
    {
      actions.add(runnable.getClass());
      runnable.call();
    }
  }
  public static <T> T run(Function0<T> runnable)
  {
    return (T) functions.computeIfAbsent(runnable.getClass(), k -> runnable.call());
  }
}
