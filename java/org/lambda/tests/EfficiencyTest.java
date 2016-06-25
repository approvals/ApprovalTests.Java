package org.lambda.tests;

import org.approvaltests.legacycode.Range;
import org.lambda.functions.Function1;
import org.lambda.functions.implementations.F1;
import org.lambda.query.Query;

import junit.framework.TestCase;

public class EfficiencyTest extends TestCase
{
  public void testFastLambdas() throws Exception
  {
    int times = 1000000;
    final Integer matching = 18;
    System.out.println(getTimeStotistics("org.lamba", times, new F1<Integer, Boolean>(0, matching)
    {
      {
        ret(a == matching);
      }
    }));
    System.out.println(getTimeStotistics("Lambda8", times, a -> a == matching));
  }
  private String getTimeStotistics(String name, int times, Function1<Integer, Boolean> function)
  {
    return String.format("Time for %s %s runs = %sms", name, times, doManyTimesWithLambda(times, function));
  }
  public long doManyTimesWithLambda(int times, Function1<Integer, Boolean> funct)
  {
    Integer[] r = Range.get(0, times);
    long start = System.currentTimeMillis();
    Query.where(r, funct);
    long end = System.currentTimeMillis();
    long diff = end - start;
    return diff;
  }
}
