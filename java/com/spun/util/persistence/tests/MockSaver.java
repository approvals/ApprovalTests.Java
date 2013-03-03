package com.spun.util.persistence.tests;

import java.util.ArrayList;

import com.spun.util.ArrayUtils;
import com.spun.util.persistence.Saver;

public class MockSaver<T> implements Saver<T>
{
  private ArrayList<T> saved = new ArrayList<T>();
  public T save(T save)
  {
    saved.add(save);
    return save;
  }
  public T getLastSaved()
  {
    return ArrayUtils.getLast(saved);
  }
}
