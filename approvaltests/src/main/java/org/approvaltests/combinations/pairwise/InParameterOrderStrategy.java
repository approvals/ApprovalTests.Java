package org.approvaltests.combinations.pairwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class InParameterOrderStrategy
{
  public static List<List<Case1>> generatePairs(List<Parameter<?>> parameters)
  {
    final List<Parameter> accumulator = new ArrayList<>();
    Stream<Parameter<?>> sortedBySize = parameters.stream()
            .sorted((o1, o2) -> Integer.compare(o2.size(), o1.size()));
    Stream<ArrayList<Parameter>> arrayListStream = sortedBySize
            .map(parameter -> {
              accumulator.add(parameter);
              return new ArrayList<>(accumulator);
            });
    return arrayListStream
            .map(chunk -> crossJoin(chunk))
            .collect(Collectors.toList());
  }
  public static List<Case1> horizontalGrowth(List<Case1> cases, List<Case1> pairs)
  {
    return cases.stream()
            .map(o -> best(pairs, o))
            .peek(o -> delete(pairs, o))
            .collect(Collectors.toList());
  }
  public static List<Case1> verticalGrowth(List<Case1> pairs)
  {
    List<Case1> collected = new ArrayList<>();
    while (!pairs.isEmpty())
    {
      Case1 pair = union(pairs, pairs.get(0));
      delete(pairs, pair);
      collected.add(pair);
    }
    return collected;
  }
  private static List<Case1> crossJoin(List<Parameter> chunk)
  {
    final Parameter multiplier = chunk.get(chunk.size() - 1);
    return new ArrayList<Case1>()
    {
      {
        IntStream.range(0, chunk.get(chunk.size() - 1).size()).forEach(last -> IntStream.range(0, chunk.size() - 1)
            .forEach(column -> IntStream.range(0, chunk.get(column).size()).forEach(cursor -> this.add(new AppleSauce()
            {
              {
                Parameter parameter = chunk.get(column);
                this.put(parameter.getPosition(), parameter.get(cursor));
                this.put(multiplier.getPosition(), multiplier.get(last));
              }
            }))));
      }
    };
  }
  private static Case1 best(List<Case1> pairs, Case1 pair)
  {
    final Map<String, String> lazyKey = new HashMap<>();
    pairs.stream().filter(pair1 -> pair.matches(pair1))
        .map(p -> p
            .get(lazyKey.computeIfAbsent("key", i -> p.keySet().stream().reduce((ignored, o) -> o).orElse(null))))
        .collect(Collectors.groupingBy(i -> i)).entrySet().stream()
        .max((o1, o2) -> Integer.compare(o1.getValue().size(), o2.getValue().size())).map(objectListEntry -> objectListEntry.getKey())
        .ifPresent(o -> pair.put(lazyKey.get("key"), o));
    return pair;
  }
  private static void delete(List<Case1> pairs, Case1 pair)
  {
    ListIterator<Case1> iterator = pairs.listIterator();
    while (iterator.hasNext())
    {
      if (iterator.next().matches(pair))
      {
        iterator.remove();
      }
    }
  }
  private static Case1 union(List<Case1> pairs, Case1 identity)
  {
    return pairs.stream().reduce(identity, (accumulator, pair) -> {
      if (accumulator.matches(pair))
      { return accumulator.union(pair); }
      return accumulator;
    });
  }
}
