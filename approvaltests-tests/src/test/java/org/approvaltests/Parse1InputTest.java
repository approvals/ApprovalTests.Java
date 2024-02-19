package org.approvaltests;

import org.junit.jupiter.api.Test;

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
  }
  @Test
  void testWithTypesTransformersAndBoth()
  {
    var expected = """
        1 -> 1
        9 -> 1001
        """;
    ParseInputWith1Parameters<String, Integer> p1 = ParseInput.from(expected).withTypes(Integer.class);
    p1.verifyAll(Integer::toBinaryString);
    p1 = ParseInput.from(expected).transformTo(Integer::parseInt);
    p1.verifyAll(Integer::toBinaryString);
    p1 = ParseInput.from(expected).withTypes(Integer.class);
    p1 = p1.transformTo(s -> s);
    p1.verifyAll(Integer::toBinaryString);
  }
}
