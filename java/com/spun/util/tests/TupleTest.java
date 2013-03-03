package com.spun.util.tests;

import com.spun.util.Tuple;
import junit.framework.TestCase;

public class TupleTest extends TestCase
{
  public void test() {
   Tuple tupleOne = new Tuple<String, Integer>("foo", 1);
   assertEquals("string first", "foo", tupleOne.getFirst());
   assertEquals("integer second", 1, tupleOne.getSecond());
  }
}
