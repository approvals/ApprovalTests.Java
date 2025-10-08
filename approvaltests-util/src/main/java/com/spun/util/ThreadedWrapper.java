package com.spun.util;

import java.util.function.Supplier;

public class ThreadedWrapper<T> implements Wrapper<T>
{
  private final ThreadLocal<T> instance;
  public ThreadedWrapper(Supplier<T> lambda)
  {
    this.instance = ThreadLocal.withInitial(lambda);
  }

  @Override
  public T get()
  {
    return instance.get();
  }
}
