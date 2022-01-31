package org.lambda.functions;

import org.approvaltests.Approvals;
import org.approvaltests.legacycode.Range;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UseReporter(TortoiseTextDiffReporter.class)
public class F1Test
{
  @Test
  public void testSquare() throws Exception
  {
    List<Integer> list = Arrays.asList(1, 2, 3, 4);
    Approvals.verifyAll("", Query.select(list, a -> a * a));
  }
  @Test
  public void testIntersection() throws Exception
  {
    List<Integer> list1 = Arrays.asList(1, 2, 3, 4);
    final List<Integer> list2 = Arrays.asList(3, 2);
    Approvals.verifyAll("", Query.where(list1, a -> list2.contains(a)));
  }
  @Test
  public void testMax() throws Exception
  {
    List<String> list = Arrays.asList("tom", "fred", "llewellyn", "john");
    assertEquals("llewellyn", Query.max(list, a -> (a.length())));
    assertEquals("tom", Query.min(list, a -> (a.length())));
  }
  @Test
  public void testEvenNumbers() throws Exception
  {
    Integer[] list = Range.get(1, 100);
    Approvals.verifyAll("", Query.where(list, a -> (a % 3 == 0)));
  }
  @Test
  public void testStatic() throws Exception
  {
    staticDouble();
  }
  private static void staticDouble() throws Exception
  {
    List<String> numbers = Arrays.asList("One", "two", "three", "four");
    Approvals.verify(Query.max(numbers, a -> a.length()));
  }
}
