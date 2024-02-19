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
    var p1 = ParseInput.from(expected).withTypes(Integer.class);
    p1.verifyAll(Integer::toBinaryString);
//    ParseInput.from(expected).transformTo(Integer::parseInt).verifyAll(Integer::toBinaryString);
//    ParseInput.from(expected).withTypes(Integer.class).transformTo(s -> s)
//        .verifyAll(s -> Integer.toBinaryString(s));
  }
}
