package org.approvaltests;

import com.spun.util.Tuple;
import org.approvaltests.reporters.AutoApproveReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.utils.parseinput.ParseInput;
import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

import java.util.Arrays;

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
    ParseInput.from(expected).transformTo(Integer::parseInt, Double::parseDouble, Boolean::parseBoolean)
        .verifyAll(t -> t.getFirst() * t.getSecond() * (t.getThird() ? 1 : 0));
    ParseInput.from(expected).withTypes(Integer.class, Double.class, Boolean.class)
        .transformTo((i, d, b) -> i * d * (b ? 1 : 0)).verifyAll(t -> t);
  }
  @Test
  @UseReporter(AutoApproveReporter.class)
  public void testArrays()
  {
    var expected = """
        lars, 20, 1,2,3 -> Person[
            name=lars
            label=teenager
        ] = 6.0
        """;
    ParseInput.from(expected).multiline().withTypes(String.class, Integer.class, Integer[].class)
        .transformTo((s, i, r) -> new Tuple<>(new Person(s, i), r))
        .verifyAll(t -> t.getFirst() + " = " + sum(t.getSecond()));
  }
  private Double sum(Integer[] integers)
  {
    return Queryable.of(integers).sum();
  }
}
