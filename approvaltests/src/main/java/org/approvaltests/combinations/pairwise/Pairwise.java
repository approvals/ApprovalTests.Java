package org.approvaltests.combinations.pairwise;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pairwise implements Iterable<Case>
{
  public List<Parameter<?>> getParameters()
  {
    return parameters;
  }
  private final List<Parameter<?>> parameters;
  public List<Case> getCases()
  {
    return cases;
  }
  private final List<Case> cases;
  private Pairwise(List<Parameter<?>> parameters, List<Case> cases)
  {
    this.parameters = parameters;
    this.cases = cases;
  }
  public List<Case> verify()
  {
    return InParameterOrderStrategy.generatePairs(parameters).stream().flatMap(cases1 -> cases1.stream())
        .filter(pair -> !stream().filter(pair1 -> pair.matches(pair1)).findFirst().isPresent()).collect(Collectors.toList());
  }
  public Object[][] toTestNG()
  {
    return stream().map(aCase -> aCase.values()).map(objects -> objects.toArray()).collect(Collectors.toList())
        .toArray(new Object[0][0]);
  }
  public Collection<Object[]> toJUnit()
  {
    return stream().map(aCase -> aCase.values()).map(objects -> objects.toArray()).collect(Collectors.toList());
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
    private static Random random = new Random(5);
    public List<Parameter<?>> getParameters()
    {
      return parameters;
    }
    public void setParameters(List<Parameter<?>> parameters)
    {
      this.parameters = parameters;
    }
    private List<Parameter<?>> parameters;
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
      final Case prototype = Case.ofLength(parameters.size());

      final Stream<List<Case>> listOfPairs = InParameterOrderStrategy.generatePairs(parameters).stream();

      final Stream<Case> reduced = listOfPairs.reduce(new ArrayList<>(), (cases, pairs) -> {
        if (cases.isEmpty())
          return pairs;
        cases = InParameterOrderStrategy.horizontalGrowth(cases, pairs);
        cases.addAll(InParameterOrderStrategy.verticalGrowth(pairs));
        return cases;
      }).stream();

      final Map<String, Object[]> params = parameters.stream()
              .collect(Collectors.toMap(objects -> objects.getName(), objects1 -> objects1.toArray()));

      return new Pairwise(parameters, reduced.map(c -> prototype.clone().union(c))
          .peek(c -> c.putAll(c.entrySet().stream().filter(e -> e.getValue() == null)
              .peek(e -> e.setValue(random(params.get(e.getKey()))))
              .collect(Collectors.toMap(stringObjectEntry -> stringObjectEntry.getKey(), stringObjectEntry1 -> stringObjectEntry1.getValue()))))
          .collect(Collectors.toList()));
    }
    private static Object random(Object[] array)
    {
      return array[random.nextInt(array.length)];
    }
  }
}
