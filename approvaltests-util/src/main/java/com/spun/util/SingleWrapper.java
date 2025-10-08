package com.spun.util;

public class SingleWrapper<T> implements Wrapper<T>
{
  private final T instance;
  public SingleWrapper(T instance)
  {
    this.instance = instance;
  }

  @Override
  public T get()
  {
    return instance;
  }
}
