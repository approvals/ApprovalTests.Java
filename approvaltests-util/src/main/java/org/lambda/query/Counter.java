package org.lambda.query;

import org.lambda.functions.Function1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Counter<T>
{
  private final Map<T, Integer> counts = new HashMap<>();
  public <O> void countAll(Collection<O> collection, Function1<O, T> extractor)
  {
    Query.select(collection, extractor).forEach(this::count);
  }

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

  public static <In, Out> Out getMaxValue(Collection<In> collection, Function1<In, Out> extractor)
  {
    Counter<Out> counter = new Counter<>();
    counter.countAll(collection, extractor);
    return counter.getMaxValue();
  }
}
