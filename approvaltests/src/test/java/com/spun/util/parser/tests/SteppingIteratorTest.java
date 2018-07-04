package com.spun.util.parser.tests;

import com.spun.util.parser.SteppingIterator;
import junit.framework.TestCase;

public class SteppingIteratorTest extends TestCase
{
  public void testIterator()
  {
    SteppingIterator si = new SteppingIterator(0, new int[] {3}, 8);
    assertEquals(si.isLast(2, 0), true);
    //assertEquals(si.isFirst(1, 0), true);
    
  }
}
