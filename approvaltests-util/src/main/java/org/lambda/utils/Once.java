package org.lambda.utils;

import org.lambda.actions.Action0;
import org.lambda.functions.Function0;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
  public static void runAsync(Action0 runnable)
  {
    if (!actions.contains(runnable.getClass()))
    {
      actions.add(runnable.getClass());
      new Thread(runnable::call).start();
    }
  }
}
