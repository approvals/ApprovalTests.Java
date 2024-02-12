package org.approvaltests;

import org.junit.jupiter.api.Test;

public class ParseInputTest
{
  @Test
  void uppercasse()
  {
    var expected = """
        a -> A
        b -> B
        hh -> HH
        c -> C
        d -> D
        e -> E
        cco -> CCO
        f -> F
        g -> G
        eue -> EUE
        aza -> AZA
        """;
    ParseInput.create(expected).verifyAll(s -> s.toUpperCase());
  }
  @Test
  void toBinary()
  {
    var expected = """
        1 -> 1
        2 -> 10
        4 -> 100
        8 -> 1000
        9 -> 1001
        """;
    ParseInput.create(expected, Integer::parseInt).verifyAll(s -> Integer.toBinaryString(s));
  }
  @Test
  void toHex()
  {
    var expected = """
        1 -> 1
        10 -> a
        16 -> 10
        22 -> 16
        """;
    ParseInput.create(expected, Integer.class).verifyAll(s -> Integer.toHexString(s));
  }
  @Test
  void testPeople()
  {
    var expected = """
        Llewellyn, 25 -> adult
        Oliver, 15 -> teenager
        """;
    Creator<Person> createPerson = (a) -> new Person(a.get(0), Integer.parseInt(a.get(1)));
    ParseInput.createFromParts(expected, createPerson).verifyAll(s -> s.getAgeLabel());
  }
  @Test
  void testPeopleEasyLoad()
  {
    var expected = """
        Llewellyn, 25 -> adult
        Oliver, 15 -> teenager
        """;
    ParseInput.createFromParts(expected, (n, a) -> new Person(n, a), String.class, Integer.class)
        .verifyAll(s -> s.getAgeLabel());
  }
}