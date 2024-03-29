package com.spun.util;

import java.util.Random;

public class MockRandom extends Random
{
  private static final long serialVersionUID = 1L;
  private int[]             returnValue;
  private int               counter          = 0;
  public MockRandom(int[] returnValue)
  {
    this.returnValue = returnValue;
  }
  protected int next(int arg0)
  {
    return returnValue[counter++ % returnValue.length];
  }
  public int nextInt()
  {
    return next(0);
  }
  public int nextInt(int arg0)
  {
    return next(arg0);
  }
}
