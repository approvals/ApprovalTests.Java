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
  public static List<List<Case>> generatePairs(List<Parameter<?>> parameters)
  {
    final List<Parameter> accumulator = new ArrayList<>();
    Stream<Parameter<?>> sortedBySize = parameters.stream()
        .sorted((o1, o2) -> Integer.compare(o2.size(), o1.size()));
    Stream<ArrayList<Parameter>> arrayListStream = sortedBySize.map(parameter -> {
      accumulator.add(parameter);
      return new ArrayList<>(accumulator);
    });
    return arrayListStream.map(chunk -> crossJoin(chunk)).collect(Collectors.toList());
  }
  public static List<Case> horizontalGrowthOld(List<Case> cases, List<Case> pairs)
  {
    return cases.stream().map(o -> best(pairs, o)).peek(o -> delete(pairs, o)).collect(Collectors.toList());
  }
  public static List<Case> horizontalGrowth(List<Case> cases, List<Case> pairs)
  {
    List<Case> result = new ArrayList<>();
    for (Case aCase : cases) {
      Case best = best(pairs, aCase);
      delete(pairs, best);
      result.add(best);
    }
    return result;
  }
  public static List<Case> verticalGrowth(List<Case> pairs)
  {
    return removeDuplicates(pairs);
  }
  public static List<Case> removeDuplicates(List<Case> pairs)
  {
    List<Case> collected = new ArrayList<>();
    for (Case aCase : pairs)
    {
      if (!collected.contains(aCase))
      {
        collected.add(aCase);
      }
    }
    return collected;
  }
  public static List<Case> crossJoin(List<Parameter> chunk)
  {
    final Parameter multiplier = chunk.get(chunk.size() - 1);
    return new ArrayList<Case>()
    {
      {
        IntStream.range(0, chunk.get(chunk.size() - 1).size()).forEach(last -> IntStream.range(0, chunk.size() - 1)
            .forEach(column -> IntStream.range(0, chunk.get(column).size()).forEach(cursor -> this.add(new Case()
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
  private static Case best(List<Case> pairs, Case pair)
  {
    final Map<String, String> lazyKey = new HashMap<>();
    pairs.stream().filter(pair1 -> pair.matches(pair1))
        .map(p -> p
            .get(lazyKey.computeIfAbsent("key", i -> p.keySet().stream().reduce((ignored, o) -> o).orElse(null))))
        .collect(Collectors.groupingBy(i -> i)).entrySet().stream()
        .max((o1, o2) -> Integer.compare(o1.getValue().size(), o2.getValue().size()))
        .map(objectListEntry -> objectListEntry.getKey()).ifPresent(o -> pair.put(lazyKey.get("key"), o));
    return pair;
  }
  private static void delete(List<Case> pairs, Case pair)
  {
    ListIterator<Case> iterator = pairs.listIterator();
    while (iterator.hasNext())
    {
      if (iterator.next().matches(pair))
      {
        iterator.remove();
      }
    }
  }
  public static List<Case> combineAppleSauce(List<Case> createManyCases, List<Case> cases)
  {
    List<Case> horizontalAndVerticalGrowth = horizontalGrowth(createManyCases, cases);
    horizontalAndVerticalGrowth.addAll(verticalGrowth(cases));
    return horizontalAndVerticalGrowth;
  }
}
