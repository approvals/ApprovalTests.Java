package com.spun.util.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VelocityList<T>
{
  private SteppingIterator iterator = null;
  private List<T>          list     = null;
  public VelocityList(T[] array)
  {
    this(array, 0, SteppingIterator.DEFAULT_STEPPING);
  }
  public VelocityList(T[] array, int offset, int stepping)
  {
    this(array, offset, new int[]{stepping});
  }
  public VelocityList(T[] array, int offset, int[] stepping)
  {
    this.list = array == null ? Collections.emptyList() : Arrays.asList(array);
    this.iterator = new SteppingIterator(offset, stepping, this.list.size());
  }
  public VelocityList(List<T> array, int offset, int[] stepping)
  {
    this.list = array == null ? Collections.emptyList() : array;
    this.iterator = new SteppingIterator(offset, stepping, this.list.size());
  }
  public VelocityList(List<T> list)
  {
    this(list, 0, SteppingIterator.DEFAULT_STEPPING);
  }
  public SteppingIterator getSteppingIterator()
  {
    return this.iterator;
  }
  private T get(int index)
  {
    int actualPosition = iterator.getActualPosition(index);
    return (actualPosition == -1) ? null : list.get(actualPosition);
  }
  public List<Item<T>> getAll()
  {
    int size = this.iterator.getSize(true, true);
    ArrayList<Item<T>> returning = new ArrayList<>(size);
    for (int i = 0; i < size; i++)
    {
      returning.add(new Item<T>(this, i));
    }
    return returning;
  }
  /*                              INNER CLASS                            */
  public static class Item<T>
  {
    VelocityList<T> list;
    int             index;
    public Item(VelocityList<T> list, int index)
    {
      this.list = list;
      this.index = index;
    }
    public T get()
    {
      return list.get(index);
    }
    public int getSize()
    {
      return list.getSteppingIterator().getSize(true, true);
    }
    public int getIndex()
    {
      return index;
    }
    public int getIndexModulous(int m)
    {
      return getIndex() % m;
    }
    public int getIndex(int offset)
    {
      return getIndex() + offset;
    }
    public boolean isCurrentIndexValid()
    {
      return list.getSteppingIterator().getActualPosition(index) != -1;
    }
    public boolean isEven()
    {
      return ((index % 2) == 0);
    }
    public boolean isLast()
    {
      return list.getSteppingIterator().isLast(index, -1);
    }
    public boolean isFirst()
    {
      return list.getSteppingIterator().isFirst(index, -1);
    }
    public boolean isLast(int i)
    {
      return list.getSteppingIterator().isLast(index, i);
    }
    public boolean isFirst(int stepping)
    {
      return list.getSteppingIterator().isFirst(index, stepping);
    }
    public int getTotalStepCountForRound(int forSteppingLevel, int indexBase)
    {
      return list.getSteppingIterator().getTotalStepCountForRound(forSteppingLevel, indexBase);
    }
    public int getStepCountForRound(int forSteppingLevel, int indexBase)
    {
      return list.getSteppingIterator().getStepCountForRound(forSteppingLevel, index, indexBase);
    }
  }
}
