package com.spun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JunitUpgrade
{
  public static void assertEquals2(String message, Object i1, Object i2)
  {
    assertEquals(i1, i2, message);
  }
  public static void assertEquals2(String message, double i1, double i2, double delta)
  {
    assertEquals(i1, i2, delta, message);
  }
  public static void assertTrue2(String message, boolean i)
  {
    assertTrue(i, message);
  }
  public static void assertFalse2(String message, boolean i)
  {
    assertFalse(i, message);
  }
}
