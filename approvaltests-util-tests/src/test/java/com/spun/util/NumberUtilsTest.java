package com.spun.util;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import com.spun.util.NumberUtils;

import junit.framework.TestCase;

public class NumberUtilsTest extends TestCase
{
  public void testFloor()
  {
    assertEquals("rounded Down", 1, NumberUtils.floor(1.0));
    assertEquals("rounded Down", 1, NumberUtils.floor(1.1));
    assertEquals("rounded Down", 1, NumberUtils.floor(1.5));
    assertEquals("rounded Down", 1, NumberUtils.floor(1.999));
  }
  public void testSignificantDigits()
  {
    for (int i = 0; i < 11; i++)
    {
      double digit = Double.valueOf(i + ".5");
      assertEquals("significant digits", i + 1, NumberUtils.setSignificantDigit(digit, 0), 0.005);
    }
  }
  public void testIntStream() throws Exception
  {
    assertIntStream(NumberUtils.toIntStream(new int[]{1, 2, 3}));
    assertIntStream(NumberUtils.toIntStream(new Integer[]{1, 2, 3}));
    assertIntStream(NumberUtils.toIntStream(Arrays.asList(1, 2, 3)));
  }
  private void assertIntStream(IntStream stream)
  {
    assertTrue(stream instanceof IntStream);
  }
  public void testLongStream() throws Exception
  {
    assertLongStream(NumberUtils.toLongStream(new long[]{1, 2, 3}));
    assertLongStream(NumberUtils.toLongStream(new Long[]{1L, 2L, 3L}));
    assertLongStream(NumberUtils.toLongStream(Arrays.asList(1L, 2L, 3L)));
  }
  private void assertLongStream(LongStream stream)
  {
    assertTrue(stream instanceof LongStream);
  }
  public void testDoubleStream() throws Exception
  {
    assertDoubleStream(NumberUtils.toDoubleStream(new double[]{1.0, 2.0, 3.0}));
    assertDoubleStream(NumberUtils.toDoubleStream(new Double[]{1.0, 2.0, 3.0}));
    assertDoubleStream(NumberUtils.toDoubleStream(Arrays.asList(1.0, 2.0, 3.0)));
  }
  private void assertDoubleStream(DoubleStream stream)
  {
    assertTrue(stream instanceof DoubleStream);
  }
}
