package com.spun.util.tests;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import junit.framework.TestCase;
import com.spun.util.ObjectUtils;

public class ObjectUtilsTest extends TestCase
{
  public void testGreatestCommonDenominator() throws Exception
  {
    Method gcd = ObjectUtils.getGreatestCommonDenominator(new Object[]{"this", new ArrayList<Object>()},"getClass");
    assertEquals(Object.class, gcd.getDeclaringClass());
  }
  /***********************************************************************/
  public void test()
  {
    Date d = new Date();
    UseCase o1 = new UseCase(1, "2", "odd", d);
    UseCase o2 = new UseCase(1, "2", "even", d);
    assertTrue(ObjectUtils.isEqualForMethods(o1, o2, new String[]{"getA", "getB", "getD"}));
    assertFalse(ObjectUtils.isEqualForMethods(o1, o2, new String[]{"getA", "getB", "getC"}));
  }
  /***********************************************************************/
  public void testGetForMethod() throws Exception
  {
    String result = ObjectUtils.getForMethod(new String[]{"one", "two", "three"}, 5, new String[]{"toString",
        "length"});
    assertEquals("three", result);
    Object[] result2 = ObjectUtils.extractArray(new String[]{"one", "two", "three"}, "length");
    TestUtils.assertEqualArray(new Integer[]{3,3,5}, result2);
  }
  /************************************************************************/
  public static void main(String[] args)
  {
    junit.textui.TestRunner.run(ObjectUtilsTest.class);
  }
  /***********************************************************************/
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
    /***********************************************************************/
    public int getA()
    {
      return a;
    }
    /***********************************************************************/
    public String getB()
    {
      return b;
    }
    /***********************************************************************/
    public String getC()
    {
      return c;
    }
    /***********************************************************************/
    public Date getD()
    {
      return d;
    }
  }
  /***********************************************************************/
  /***********************************************************************/
}
