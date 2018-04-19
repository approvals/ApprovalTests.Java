package com.spun.util.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.spun.util.Tuple;

public class TupleTest
{
  @Test
  public void test()
  {
    Tuple<String, Integer> tupleOne = new Tuple<String, Integer>("foo", 1);
    assertEquals("string first", "foo", tupleOne.getFirst());
    assertEquals("integer second", 1, tupleOne.getSecond().intValue());
  }
}
