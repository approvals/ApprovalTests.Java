package com.spun.util.persistence;

public interface Saver<T>
{
  // begin-snippet: saver_interface
  public T save(T save);
  // end-snippet
}
