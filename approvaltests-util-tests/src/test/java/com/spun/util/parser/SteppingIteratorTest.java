package com.spun.util.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SteppingIteratorTest
{
  public void testIterator()
  {
    SteppingIterator si = new SteppingIterator(0, new int[]{3}, 8);
    assertTrue(si.isLast(2, 0));
    assertTrue(si.isFirst(1, 0));
  }
}
