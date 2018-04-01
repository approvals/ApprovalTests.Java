package com.spun.util.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/***********************************************************************/
public class VelocityList
{
  private SteppingIterator iterator = null;
  @SuppressWarnings("unchecked")
  private List             list     = null;
  public VelocityList(Object[] array)
  {
    this(array, 0, SteppingIterator.DEFAULT_STEPPING);
  }
  /***********************************************************************/
  public VelocityList(Object[] array, int offset, int stepping)
  {
    this(array, offset, new int[]{stepping});
  }
  /***********************************************************************/
  public VelocityList(Object[] array, int offset, int[] stepping)
  {
    this.list = array == null ? Collections.EMPTY_LIST : Arrays.asList(array);
    this.iterator = new SteppingIterator(offset, stepping, this.list.size());
  }
  /***********************************************************************/
  public VelocityList(List array, int offset, int[] stepping)
  {
    this.list = array == null ? Collections.EMPTY_LIST : array;
    this.iterator = new SteppingIterator(offset, stepping, this.list.size());
  }
  /***********************************************************************/
  @SuppressWarnings("unchecked")
  public VelocityList(List list2)
  {
    this(list2 == null ? null : list2.toArray());
  }
  /***********************************************************************/
  public SteppingIterator getSteppingIterator()
  {
    return this.iterator;
  }
  /***********************************************************************/
  private Object get(int index)
  {
    int actualPosition = iterator.getActualPosition(index);
    return (actualPosition == -1) ? null : list.get(actualPosition);
  }
  /***********************************************************************/
  public List<Item> getAll()
  {
    int size = this.iterator.getSize(true, true);
    ArrayList<Item> returning = new ArrayList<Item>(size);
    for (int i = 0; i < size; i++)
    {
      returning.add(new Item(this, i));
    }
    return returning;
  }
  /***********************************************************************/
  public static class Item
  {
    VelocityList list;
    int          index;
    public Item(VelocityList list, int index)
    {
      this.list = list;
      this.index = index;
    }
    public Object get()
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
