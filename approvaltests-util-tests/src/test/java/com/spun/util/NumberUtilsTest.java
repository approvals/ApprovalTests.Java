package com.spun.util;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberUtilsTest
{
  @Test
  public void testFloor()
  {
    assertEquals(1, (Object) NumberUtils.floor(1.0), "rounded Down");
    assertEquals(1, (Object) NumberUtils.floor(1.1), "rounded Down");
    assertEquals(1, (Object) NumberUtils.floor(1.5), "rounded Down");
    assertEquals(1, (Object) NumberUtils.floor(1.999), "rounded Down");
  }
  @Test
  public void testSignificantDigits()
  {
    for (int i = 0; i < 11; i++)
    {
      double digit = Double.valueOf(i + ".5");
      assertEquals(i + 1, NumberUtils.setSignificantDigit(digit, 0), 0.005, "significant digits");
    }
  }
  @Test
  public void testIntStream()
  {
    assertIntStream(NumberUtils.toIntStream(new int[]{1, 2, 3}));
    assertIntStream(NumberUtils.toIntStream(new Integer[]{1, 2, 3}));
    assertIntStream(NumberUtils.toIntStream(Arrays.asList(1, 2, 3)));
  }
  private void assertIntStream(IntStream stream)
  {
    assertTrue(stream instanceof IntStream);
  }
  @Test
  public void testLongStream()
  {
    assertLongStream(NumberUtils.toLongStream(new long[]{1, 2, 3}));
    assertLongStream(NumberUtils.toLongStream(new Long[]{1L, 2L, 3L}));
    assertLongStream(NumberUtils.toLongStream(Arrays.asList(1L, 2L, 3L)));
  }
  private void assertLongStream(LongStream stream)
  {
    assertTrue(stream instanceof LongStream);
  }
  @Test
  public void testDoubleStream()
  {
    assertDoubleStream(NumberUtils.toDoubleStream(new double[]{1.0, 2.0, 3.0}));
    assertDoubleStream(NumberUtils.toDoubleStream(new Double[]{1.0, 2.0, 3.0}));
    assertDoubleStream(NumberUtils.toDoubleStream(Arrays.asList(1.0, 2.0, 3.0)));
  }
  private void assertDoubleStream(DoubleStream stream)
  {
    assertTrue(stream instanceof DoubleStream);
  }
  @Test
  void testStripNonNumeric()
  {
    String[] inputs = {"11.0", "19-ea", "", null, "19", "1.8.0"};
    Approvals.verifyAll("Strip non numeric", inputs,
        s -> String.format("%s -> %s", s, NumberUtils.stripNonNumeric(s)));
  }
}
