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
    ParseInput.from(expected).withTypes(Integer.class, Double.class).verifyAll((i, d) -> i * d);
    ParseInput.from(expected).transformTo(Integer::parseInt, Double::parseDouble)
        .verifyAll(t -> t.getFirst() * t.getSecond());
    ParseInput.from(expected).withTypes(Integer.class, Double.class).transformTo((i, d) -> i * d)
        .verifyAll(t -> t);
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
}
