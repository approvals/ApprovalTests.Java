package org.approvaltests.legacycode;

import java.util.Iterator;

public class IndexPermutations implements Iterable<Integer[]>, Iterator<Integer[]>
{
  private final Integer[] sizes;
  private Integer[]       index;
  private boolean         finished = false;
  public IndexPermutations(Integer[] sizes)
  {
    this.sizes = sizes;
    this.index = new Integer[sizes.length];
    for (int i = 0; i < sizes.length; i++)
    {
      index[i] = 0;
    }
  }
  @Override
  public Iterator<Integer[]> iterator()
  {
    return this;
  }
  @Override
  public boolean hasNext()
  {
    return !finished;
  }
  @Override
  public Integer[] next()
  {
    Integer[] r = index.clone();
    incermentIndex(0);
    return r;
  }
  private void incermentIndex(int index)
  {
    this.index[index]++;
    if (this.index[index].equals(sizes[index]))
    {
      if (index == sizes.length - 1)
      {
        finished = true;
        return;
      }
      this.index[index] = 0;
      incermentIndex(index + 1);
    }
  }
  @Override
  public void remove()
  {
    throw new RuntimeException("remove not implemented for IndexPermutations");
  }
}
