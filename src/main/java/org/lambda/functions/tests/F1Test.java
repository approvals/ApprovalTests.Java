package org.lambda.functions.tests;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.legacycode.Range;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;
import org.lambda.functions.implementations.F0;
import org.lambda.functions.implementations.F1;
import org.lambda.functions.implementations.S1;
import org.lambda.query.Query;

@UseReporter(TortoiseTextDiffReporter.class)
public class F1Test extends TestCase
{
  public void testSquare() throws Exception
  {
    List<Integer> list = Arrays.asList(1, 2, 3, 4);
    Approvals.verifyAll("", Query.select(list, new F1<Integer, Integer>(0)
    {
      {
        returnValue(a * a);
      }
    }));
  }
  public void testIntersection() throws Exception
  {
    List<Integer> list1 = Arrays.asList(1, 2, 3, 4);
    final List<Integer> list2 = Arrays.asList(3, 2);
    Approvals.verifyAll("", Query.where(list1, new F1<Integer, Boolean>(0, list2)
    {
      {
        returnValue(list2.contains(a));
      }
    }));
  }
  public void testMax() throws Exception
  {
    List<String> list = Arrays.asList("tom", "fred", "llewellyn", "john");
    assertEquals("llewellyn", Query.max(list, new S1<String>("")
    {
      {
        returnValue(a.length());
      }
    }));
    assertEquals("tom", Query.min(list, new S1<String>("")
    {
      {
        returnValue(a.length());
      }
    }));
  }
  public void testEvenNumbers() throws Exception
  {
    Integer[] list = Range.get(1, 100);
    Approvals.verifyAll("", Query.where(list, new F1<Integer, Boolean>(0)
    {
      {
        returnValue(a % 3 == 0);
      }
    }));
  }
  public void testStatic() throws Exception
  {
    staticDouble();
  }
  private static void staticDouble() throws Exception
  {
    List<String> numbers = Arrays.asList("One", "two", "three", "four");
    Approvals.verify(Query.max(numbers, new S1<String>("")
    {
      {
        ret(a.length());
      }
    }));
  }
  public void testUsingParentObject() throws Exception
  {
    assertEquals("5", new F0<String>()
    {
      {
        ret(getFive());
      }
    }.call());
  }
  private String getFive()
  {
    return "5";
  }
}
