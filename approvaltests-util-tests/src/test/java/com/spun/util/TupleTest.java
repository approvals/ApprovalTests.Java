package com.spun.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TupleTest
{
  @Test
  public void test()
  {
    Tuple<String, Integer> tupleOne = new Tuple<String, Integer>("foo", 1);
    assertEquals((Object) "foo", tupleOne.getFirst(), "string first");
    assertEquals(1, (Object) tupleOne.getSecond().intValue(), "integer second");
  }
}
