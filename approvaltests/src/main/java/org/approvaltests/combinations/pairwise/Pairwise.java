package org.approvaltests.combinations.pairwise;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pairwise implements Iterable<AppleSauce>
{
  private final List<Parameter<?>> parameters;
  public List<Parameter<?>> getParameters()
  {
    return parameters;
  }
  public List<AppleSauce> getCases()
  {
    return cases;
  }
  private final List<AppleSauce> cases;
  private Pairwise(List<Parameter<?>> parameters, List<AppleSauce> cases)
  {
    this.parameters = parameters;
    this.cases = cases;
  }
  public List<AppleSauce> verify()
  {
    return InParameterOrderStrategy.generatePairs(parameters).stream().flatMap(cases1 -> cases1.stream())
        .filter(pair -> !stream().filter(pair1 -> pair.matches(pair1)).findFirst().isPresent())
        .collect(Collectors.toList());
  }
  @Override
  public Iterator<AppleSauce> iterator()
  {
    return cases.iterator();
  }
  public Stream<AppleSauce> stream()
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
      final AppleSauce prototype = AppleSauce.ofLength(parameters.size());
      final Stream<List<AppleSauce>> listOfPairs = InParameterOrderStrategy.generatePairs(parameters).stream();
      final Stream<AppleSauce> reduced = listOfPairs.reduce(
              new ArrayList<>(),
              (cases, pairs) -> foobar(cases, pairs)).stream();

      final Map<String, Object[]> params = parameters.stream()
          .collect(Collectors.toMap(objects -> objects.getPosition(), objects1 -> objects1.toArray()));

      List<AppleSauce> parameters = reduced.map(c -> prototype.clone().union(c))
              .peek(c -> foo(params, c))
              .collect(Collectors.toList());

      return new Pairwise(this.parameters, parameters);
    }

    public List<AppleSauce> foobar(List<AppleSauce> cases, List<AppleSauce> pairs) {
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
