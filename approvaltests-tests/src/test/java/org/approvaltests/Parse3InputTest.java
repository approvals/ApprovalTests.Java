package org.approvaltests;

import org.approvaltests.reporters.AutoApproveReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.utils.parseinput.ParseInput;
import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

public class Parse3InputTest
{
  @Test
  void testWithTypesTransformersAndBoth()
  {
    var expected = """
        1,2.2,true -> 2.2
        1,3.3,false -> 0.0
        """;
    ParseInput.from(expected).withTypes(Integer.class, Double.class, Boolean.class)
        .verifyAll(t -> t.getFirst() * t.getSecond() * (t.getThird() ? 1 : 0));
    ParseInput.from(expected).withTypes(Integer.class, Double.class, Boolean.class)
        .verifyAll((i, d, b) -> i * d * (b ? 1 : 0));
    //    ParseInput.from(expected).transformTo(Integer::parseInt, Double::parseDouble)
    //        .verifyAll(t -> t.getFirst() * t.getSecond());
    //    ParseInput.from(expected).withTypes(Integer.class, Double.class).transformTo((i, d) -> i * d)
    //        .verifyAll(t -> t);
  }
  @Test
  void testPersonAge()
  {
    var expected = """
        Llewellyn, 25 -> Person[
            name=Llewellyn
            label=adult
        ]
        Oliver, 15 -> Person[
            name=Oliver
            label=teenager
        ]
        """;
    ParseInput.from(expected).multiline().withTypes(String.class, Integer.class).transformTo(Person::new)
        .verifyAll(Person::toString);
  }
  @Test
  @UseReporter(AutoApproveReporter.class)
  public void testArrays()
  {
    var expected = """
        1, 1 -> 2.0
        10 ,1, 1 -> 12.0
        5,5,7,7 -> 24.0
        """;
    ParseInput.from(expected).withTypes(Integer[].class).verifyAll(this::sum);
  }
  private Double sum(Integer[] integers)
  {
    return Queryable.of(integers).sum();
  }
}
