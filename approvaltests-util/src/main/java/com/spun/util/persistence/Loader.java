package com.spun.util.persistence;

public interface Loader<T>
{
  // begin-snippet: loader_interface
  public T load();
  // end-snippet
}
