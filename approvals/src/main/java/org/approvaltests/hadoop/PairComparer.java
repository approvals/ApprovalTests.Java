package org.approvaltests.hadoop;

import java.util.Comparator;

import org.apache.hadoop.mrunit.types.Pair;

public class PairComparer<S, T> implements Comparator<Pair<S, T>>
{
  public static final PairComparer<Object, Object> INSTANCE = new PairComparer<Object, Object>();
  @SuppressWarnings("unchecked")
  public int compare(Pair<S, T> o1, Pair<S, T> o2)
  {
    return ((Comparable<S>) o1.getFirst()).compareTo(o2.getFirst());
  }
}