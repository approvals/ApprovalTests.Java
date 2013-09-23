package com.spun.util.persistence.tests;

import com.spun.util.persistence.Loader;

public class MockLoader<T> implements Loader<T>
{
  private T item;
  public MockLoader(T item)
  {
    this.item = item;
  }
  public T load() throws Exception
  {
    return item;
  }
}
