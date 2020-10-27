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
      final List<List<Case>> listOfPairs = InParameterOrderStrategy.generatePairs(parameters);
      List<Case> minimalCases = createEssentialCasesWithGaps(listOfPairs);
      return fillGaps(combineParametersToMap(parameters), minimalCases);
    }
    public static Map<String, Object[]> combineParametersToMap(List<Parameter<?>> parameters)
    {
      final Map<String, Object[]> params = parameters.stream()
          .collect(Collectors.toMap(objects -> objects.getPosition(), objects1 -> objects1.toArray()));
      return params;
    }
    public static List<Case> createEssentialCasesWithGaps(List<List<Case>> listOfPairs)
    {
      List<Case> createManyCases = new ArrayList<>();
      for (List<Case> cases : listOfPairs)
      {
        if (createManyCases.isEmpty())
        {
          createManyCases = cases;
        }
        else
        {
          createManyCases = InParameterOrderStrategy.combineAppleSauce(createManyCases, cases);
        }
      }
      return createManyCases;
    }
    public static List<Case> fillGaps(Map<String, Object[]> params, List<Case> createManyCases)
    {
      return Query.select(createManyCases, c -> c.replaceNullsWithRandomParameters(params));
    }
  }
}
