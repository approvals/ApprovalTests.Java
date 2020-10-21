package org.approvaltests.combinations.pairwise;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.lambda.query.Query;

public class Pairwise implements Iterable<Case>
{
  private final List<Parameter<?>> parameters;
  public List<Parameter<?>> getParameters()
  {
    return parameters;
  }
  public List<Case> getCases()
  {
    return cases;
  }
  private final List<Case> cases;
  private Pairwise(List<Parameter<?>> parameters, List<Case> cases)
  {
    this.parameters = parameters;
    this.cases = (List<Case>) (Object) cases;
  }
  public List<Case> verify()
  {
    return InParameterOrderStrategy.generatePairs(parameters).stream().flatMap(cases1 -> cases1.stream())
        .filter(pair -> !stream().filter(pair1 -> pair.matches(pair1)).findFirst().isPresent())
        .collect(Collectors.toList());
  }
  @Override
  public Iterator<Case> iterator()
  {
    return cases.iterator();
  }
  public Stream<Case> stream()
  {
    return cases.stream();
  }
  public static class Builder
  {
    private List<Parameter<?>> parameters;
    public List<Parameter<?>> getParameters()
    {
      return parameters;
    }
    public void setParameters(List<Parameter<?>> parameters)
    {
      this.parameters = parameters;
    }
    public Builder()
    {
      this.parameters = new ArrayList<>();
    }
    public Builder withParameter(Parameter<?> parameter)
    {
      this.parameters.add(parameter);
      return this;
    }
    public Builder withParameters(List<Parameter<?>> parameters)
    {
      this.parameters.addAll(parameters);
      return this;
    }
    public Pairwise build()
    {
      List<Case> minimalCases = getMinimalCases(parameters);
      return new Pairwise(this.parameters, minimalCases);
    }
    public static List<Case> getMinimalCases(List<Parameter<?>> parameters)
    {
      final Stream<List<Case>> listOfPairs = InParameterOrderStrategy.generatePairs(parameters).stream();
      final Map<String, Object[]> params = parameters.stream()
          .collect(Collectors.toMap(objects -> objects.getPosition(), objects1 -> objects1.toArray()));
      final Case prototype = Case.ofLength(parameters.size());
      List<Case> minimalCases = appleSauce2(listOfPairs, params);
      return minimalCases;
    }
    public static List<Case> appleSauce(Stream<List<Case>> listOfPairs, Map<String, Object[]> params,
        Case prototype)
    {
      List<Case> minimalCases = listOfPairs.reduce(new ArrayList<>(), (cases1, pairs) -> foobar(cases1, pairs))
          .stream().map(c -> prototype.clone().union(c)).peek(c -> foo(params, c)).collect(Collectors.toList());
      return minimalCases;
    }
    public static List<Case> appleSauce2(Stream<List<Case>> listOfPairs, Map<String, Object[]> params)
    {
      List<List<Case>> listOfPairs1 = listOfPairs.collect(Collectors.toList());
      List<Case> createManyCases = new ArrayList<>();
      for (List<Case> cases : listOfPairs1)
      {
        if (createManyCases.isEmpty())
        {
          createManyCases = cases;
        }
        else
        {
          List<Case> horizontalAndVerticalGrowth = InParameterOrderStrategy.horizontalGrowth(createManyCases,
              cases);
          horizontalAndVerticalGrowth.addAll(InParameterOrderStrategy.verticalGrowth(cases));
          createManyCases = horizontalAndVerticalGrowth;
        }
      }
      return fillGaps(params, createManyCases);
    }
    public static List<Case> fillGaps(Map<String, Object[]> params, List<Case> createManyCases)
    {
      return Query.select(createManyCases, c -> c.replaceNullsWithRandomParameters(params));
    }
    public static List<Case> foobar(List<Case> cases, List<Case> pairs)
    {
      if (cases.isEmpty())
        return pairs;
      cases = InParameterOrderStrategy.horizontalGrowth(cases, pairs);
      cases.addAll(InParameterOrderStrategy.verticalGrowth(pairs));
      return cases;
    }
    public static void foo(Map<String, Object[]> params, Case appleSauce)
    {
      Stream<Map.Entry<String, Object>> fillNullWithRandom = appleSauce.entrySet().stream()
          .filter(e -> e.getValue() == null).peek(e -> e.setValue(Case.random(params.get(e.getKey()))));
      Map<String, Object> result = fillNullWithRandom.collect(Collectors.toMap(
          stringObjectEntry -> stringObjectEntry.getKey(), stringObjectEntry1 -> stringObjectEntry1.getValue()));
      appleSauce.putAll(result);
    }
  }
}
