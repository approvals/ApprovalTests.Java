package com.spun.util;

import org.junit.jupiter.api.Test;
import org.lambda.functions.Functions;
import org.lambda.query.Queryable;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
  @Test
  void throwAsErrorExample()
  {
    // begin-snippet: throw_as_error
    try
    {
      methodThatMightThrowCheckedException();
      methodThatMightThrowRuntimeException();
      methodThatMightThrowError();
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
    // end-snippet
  }

  @Test
  void throwLambdaExecution()
  {
    // begin-snippet: throw_as_error_lambda
    ObjectUtils.throwAsError(() -> methodThatMightThrowCheckedException());
    int i = ObjectUtils.throwAsError(() -> methodThatMightThrowCheckedExceptionWithReturnValue());
    // end-snippet
  }

  @Test
  void uncheckedLambdas()
  {
    Queryable<String> files = Queryable.as("1.txt", "2.txt", "3.txt");
    // begin-snippet: throw_as_unchecked
    Queryable<String> paths = files.select(Functions.unchecked(
        // throws IOException
        s -> new File(s).getCanonicalPath()));
    // end-snippet
  }

  private int methodThatMightThrowCheckedExceptionWithReturnValue() throws Exception
  {
    return 1;
  }

  private void methodThatMightThrowError() throws Error
  {
  }

  private void methodThatMightThrowRuntimeException() throws RuntimeException
  {
  }

  private void methodThatMightThrowCheckedException() throws Exception
  {
  }
}
