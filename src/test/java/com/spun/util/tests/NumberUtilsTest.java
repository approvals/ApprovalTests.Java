package com.spun.util.tests;

import junit.framework.TestCase;
import com.spun.util.NumberUtils;

public class NumberUtilsTest extends TestCase
{
  /***********************************************************************/
  public void testFloor()
  {
    assertEquals("rounded Down", 1, NumberUtils.floor(1.0));
    assertEquals("rounded Down", 1, NumberUtils.floor(1.1));
    assertEquals("rounded Down", 1, NumberUtils.floor(1.5));
    assertEquals("rounded Down", 1, NumberUtils.floor(1.999));
  }
  /***********************************************************************/
  public void testSignificantDigits()
  {
    for (int i = 0; i < 11; i++)
    {
      double digit = Double.valueOf(i + ".5");
      assertEquals("significant digits", i + 1, NumberUtils.setSignificantDigit(digit, 0), 0.005);
    }
  }
  /***********************************************************************/
  /***********************************************************************/
}
