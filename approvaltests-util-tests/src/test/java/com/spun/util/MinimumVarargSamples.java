package com.spun.util;

import org.junit.jupiter.api.Test;

public class MinimumVarargSamples
{
  @Test
  void testCompilation()
  {
    try {
      // begin-snippet: minimalVarargsException
      int smallest = findSmallest();
      // end-snippet
    }
    catch (IllegalArgumentException e)
    {

    }
  }
  // begin-snippet: minimalVarargsRuntime
  public Integer findSmallest(Integer... numbers)
  {
    if (numbers == null || numbers.length < 1)
    { throw new IllegalArgumentException("you must have at least one number"); }
    // rest of the code
    // end-snippet
    return 42;
  }
  // begin-snippet: minimalVarargsCompileTime
  public Integer findSmallest(Integer first, Integer... numbers)
  {
    Integer[] combined = ArrayUtils.combine(first, numbers);
    // rest of the code
    // end-snippet
    return 42;
  }
}
