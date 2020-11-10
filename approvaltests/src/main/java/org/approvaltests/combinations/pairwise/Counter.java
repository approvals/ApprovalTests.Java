package org.approvaltests.combinations.pairwise;

import java.util.HashMap;
import java.util.Map;

import org.lambda.query.Query;

public class Counter<T>
{
  private final Map<T, Integer> counts = new HashMap<>();
  public void count(T value)
  {
    Integer count = counts.computeIfAbsent(value, x -> 0) + 1;
    counts.put(value, count);
  }
  public T getMaxValue()
  {
    if (counts.isEmpty())
    { return null; }
    return Query.max(counts.entrySet(), Map.Entry::getValue).getKey();
  }
}
