package org.approvaltests.combinations.pairwise;

import org.lambda.query.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pairwise implements Iterable<Case>
{
  private final List<OptionsForAParameter<?>> parameters;
  private final List<Case>                    cases;
  private Pairwise(List<OptionsForAParameter<?>> parameters, List<Case> cases)
  {
    this.parameters = parameters;
    this.cases = (List<Case>) (Object) cases;
  }

  public static Pairwise toPairWise(Object[]... parameters)
  {
    ArrayList<OptionsForAParameter<?>> list = new ArrayList<>();
    for (int i = 0; i < parameters.length; i++)
    {
      list.add(new OptionsForAParameter<>(i, parameters[i]));
    }
    return new Builder().withParameters(list).build();
  }

  public int getTotalPossibleCombinations()
  {
    int totalPossibleSize = 1;
    for (OptionsForAParameter<?> parameter : getParameters())
    {
      totalPossibleSize *= parameter.size();
    }
    return totalPossibleSize;
  }

  public List<OptionsForAParameter<?>> getParameters()
  {
    return parameters;
  }

  public List<Case> getCases()
  {
    return cases;
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
    private List<OptionsForAParameter<?>> parameters;
    public List<OptionsForAParameter<?>> getParameters()
    {
      return parameters;
    }

    public void setParameters(List<OptionsForAParameter<?>> parameters)
    {
      this.parameters = parameters;
    }

    public Builder()
    {
      this.parameters = new ArrayList<>();
    }

    public Builder withParameter(OptionsForAParameter<?> parameter)
    {
      this.parameters.add(parameter);
      return this;
    }

    public Builder withParameters(List<OptionsForAParameter<?>> parameters)
    {
      this.parameters.addAll(parameters);
      return this;
    }

    public Pairwise build()
    {
      List<Case> minimalCases = getMinimalCases(parameters);
      Case.resetRandom();
      return new Pairwise(this.parameters, minimalCases);
    }

    public static List<Case> getMinimalCases(List<OptionsForAParameter<?>> parameters)
    {
      final List<List<Case>> listOfPairs = InParameterOrderStrategy.generatePairs(parameters);
      List<Case> minimalCases = createEssentialCasesWithGaps(listOfPairs);
      return fillGaps(combineParametersToMap(parameters), minimalCases);
    }

    public static Map<String, Object[]> combineParametersToMap(List<OptionsForAParameter<?>> parameters)
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
