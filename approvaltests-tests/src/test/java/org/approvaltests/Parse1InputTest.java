package org.approvaltests;

import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

public class Parse1InputTest
{
  @Test
  void testSimpleString()
  {
    var expected = """
        a -> A
        b -> B
        """;
    ParseInput.from(expected).verifyAll(String::toUpperCase);
    Approvals.verifyAll(ParseInput.from(expected).getInputs(), s -> s + " -> " + s.toUpperCase(),
        new Options().inline(expected));
  }
  @Test
  void testWithTypesTransformersAndBoth()
  {
    var expected = """
        1 -> 1
        9 -> 1001
        """;
    ParseInput.from(expected).withTypes(Integer.class).verifyAll(Integer::toBinaryString);
    ParseInput.from(expected).transformTo(Integer::parseInt).verifyAll(Integer::toBinaryString);
    ParseInput.from(expected).withTypes(String.class).transformTo(Integer::parseInt)
        .verifyAll(Integer::toBinaryString);
    // the hard way
    Queryable<Integer> inputs = ParseInput.from(expected).withTypes(Integer.class).getInputs();
    Approvals.verifyAll(inputs, i -> i + " -> " + Integer.toBinaryString(i), new Options().inline(expected));
  }
}
