package org.approvaltests.combinations.pairwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
  public static List<Case> horizontalGrowth(List<Case> cases, List<Case> pairs)
  {
    List<Case> result = new ArrayList<>();
    for (Case aCase : cases)
    {
      Case best = best(pairs, aCase);
      pairs.removeIf(p -> p.matches(aCase));
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
                // we believe this creates problematic order but we're unsure.
                Parameter parameter = chunk.get(column);
                this.put(parameter.getPosition(), parameter.get(cursor));
                this.put(multiplier.getPosition(), multiplier.get(last));
              }
            }))));
      }
    };
  }
  private static Case best(List<Case> pairs, Case aCaseParameter)
  {
    String key = null;
    Map<Object, Integer> lastKeyCounts = new HashMap<>();
    int amount = 0;
    Object obj = null;
    for (Case aCase : pairs)
    {
      if (aCaseParameter.matches(aCase))
      {
        if (key == null)
        {
          key = aCase.getLastKey();
        }
        Object value = aCase.get(key);
        Integer count = lastKeyCounts.computeIfAbsent(value, x -> 0) + 1;
        lastKeyCounts.put(value, count);
      }
    }
    for (Object o : lastKeyCounts.keySet())
    {
      int size = lastKeyCounts.get(o);
      if (amount < size)
      {
        obj = o;
        amount = size;
      }
    }
    if (obj != null)
    {
      aCaseParameter.put(key, obj);
    }
    return aCaseParameter;
  }
  public static List<Case> combineAppleSauce(List<Case> createManyCases, List<Case> cases)
  {
    List<Case> horizontalAndVerticalGrowth = horizontalGrowth(createManyCases, cases);
    horizontalAndVerticalGrowth.addAll(verticalGrowth(cases));
    return horizontalAndVerticalGrowth;
  }
}
