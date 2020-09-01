package org.approvaltests.combinations.pairwise;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pairwise implements Iterable<Case1>
{
  private final List<Parameter<?>> parameters;
  public List<Parameter<?>> getParameters()
  {
    return parameters;
  }
  public List<Case1> getCases()
  {
    return cases;
  }
  private final List<Case1> cases;
  private Pairwise(List<Parameter<?>> parameters, List<Case1> cases)
  {
    this.parameters = parameters;
    this.cases = (List<Case1>) (Object) cases;
  }
  public List<Case1> verify()
  {
    return InParameterOrderStrategy.generatePairs(parameters).stream().flatMap(cases1 -> cases1.stream())
        .filter(pair -> !stream().filter(pair1 -> pair.matches(pair1)).findFirst().isPresent())
        .collect(Collectors.toList());
  }
  @Override
  public Iterator<Case1> iterator()
  {
    return cases.iterator();
  }
  public Stream<Case1> stream()
  {
    return cases.stream();
  }
  public static class Builder
  {
    private static Random      random = new Random(5);
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
      final Case1 prototype = AppleSauce.ofLength(parameters.size());
      final Stream<List<Case1>> listOfPairs = InParameterOrderStrategy.generatePairs(parameters).stream();
      List<Case1> reduce = listOfPairs.reduce(
              new ArrayList<>(),
              (cases, pairs) -> foobar(cases, pairs));

      final Stream<Case1> reduced = reduce.stream();

      final Map<String, Object[]> params = parameters.stream()
          .collect(Collectors.toMap(objects -> objects.getPosition(), objects1 -> objects1.toArray()));

      List<Case1> parameters = reduced.map(c -> prototype.clone().union(c))
              .peek(c -> foo(params, c))
              .collect(Collectors.toList());

      return new Pairwise(this.parameters, parameters);
    }

    public List<Case1> foobar(List<Case1> cases, List<Case1> pairs) {
      if (cases.isEmpty())
        return pairs;
      cases = InParameterOrderStrategy.horizontalGrowth(cases, pairs);
      cases.addAll(InParameterOrderStrategy.verticalGrowth(pairs));
      return cases;
    }

    public void foo(Map<String, Object[]> params, Case1 appleSauce)
    {
      Stream<Map.Entry<String, Object>> fillNullWithRandom = appleSauce.entrySet().stream()
              .filter(e -> e.getValue() == null)
              .peek(e -> e.setValue(random(params.get(e.getKey()))));

      Map<String, Object> result = fillNullWithRandom.collect(
              Collectors.toMap(
                      stringObjectEntry -> stringObjectEntry.getKey(),
                      stringObjectEntry1 -> stringObjectEntry1.getValue()));
      appleSauce.putAll(result);
    }
    private static Object random(Object[] array)
    {
      return array[random.nextInt(array.length)];
    }
  }
}
