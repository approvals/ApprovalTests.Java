package com.spun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JunitUpgrade
{
  public static void assertEquals2(String message, Object i1, Object i2)
  {
    assertEquals(i1, i2, message);
  }
  public static void assertTrue2(String message, boolean i)
  {
    assertTrue(i, message);
  }
}
