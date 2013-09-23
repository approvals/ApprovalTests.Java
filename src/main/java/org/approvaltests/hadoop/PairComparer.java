package org.approvaltests.hadoop;

import java.util.Comparator;

import org.apache.hadoop.mrunit.types.Pair;

public class PairComparer implements Comparator<Pair>
{
  public static final PairComparer INSTANCE = new PairComparer();
  public int compare(Pair o1, Pair o2)
  {
    return ((Comparable) o1.getFirst()).compareTo(o2.getFirst());
  }
}