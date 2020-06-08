package com.spun.util.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.spun.util.ObjectUtils;

public class ObjectUtilsTest
{
  @Test
  public void testGreatestCommonDenominator() throws Exception
  {
    Method gcd = ObjectUtils.getGreatestCommonDenominator(new Object[]{"this", new ArrayList<Object>()},
        "getClass");
    assertEquals(Object.class, gcd.getDeclaringClass());
  }

  @Test
  public void test()
  {
    Date d = new Date();
    UseCase o1 = new UseCase(1, "2", "odd", d);
    UseCase o2 = new UseCase(1, "2", "even", d);
    assertTrue(ObjectUtils.isEqualForMethods(o1, o2, "getA", "getB", "getD"));
    assertFalse(ObjectUtils.isEqualForMethods(o1, o2, "getA", "getB", "getC"));
  }

  public static class UseCase
  {
    private int    a;
    private String b;
    private String c;
    private Date   d;
    public UseCase(int a, String b, String c, Date d)
    {
      this.a = a;
      this.b = b;
      this.c = c;
      this.d = d;
    }

    public int getA()
    {
      return a;
    }

    public String getB()
    {
      return b;
    }

    public String getC()
    {
      return c;
    }

    public Date getD()
    {
      return d;
    }
  }


}
