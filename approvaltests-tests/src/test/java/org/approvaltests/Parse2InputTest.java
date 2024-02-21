package org.approvaltests;

import org.junit.jupiter.api.Test;

public class Parse2InputTest
{
  @Test
  void testWithTypesTransformersAndBoth()
  {
    var expected = """
        1,2.2 -> 2.2
        1,3.3 -> 3.3
        """;
    ParseInput.from(expected).withTypes(Integer.class, Double.class).verifyAll(t -> t.getFirst() * t.getSecond());
    // TODO: continue here
    //    ParseInput.from(expected).transformTo(Integer::parseInt).verifyAll(Integer::toBinaryString);
    //    ParseInput.from(expected).withTypes(String.class).transformTo(Integer::parseInt).verifyAll(Integer::toBinaryString);
  }
}
