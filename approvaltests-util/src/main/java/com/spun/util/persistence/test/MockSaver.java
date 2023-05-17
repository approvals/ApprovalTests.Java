package com.spun.util.persistence.test;

import com.spun.util.ArrayUtils;
import com.spun.util.persistence.Saver;

import java.util.ArrayList;

public class MockSaver<T> implements Saver<T>
{
  public ArrayList<T> saved = new ArrayList<T>();
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
