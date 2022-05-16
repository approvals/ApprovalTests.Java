package com.spun.util;

import org.junit.jupiter.api.Test;

import static com.spun.util.ComparableUtils.wrap;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComparableUtilsTest
{
  @Test
  void testWrapper()
  {
    Integer one = 1;
    Integer two = 2;
    assertTrue(wrap(one).isLessThan(two));
    assertTrue(wrap(one).isLessThanOrEqual(two));
    assertTrue(wrap(one).isEqualTo(one));
  }
}
