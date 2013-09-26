package org.lambda.tests;

import java.text.MessageFormat;

import junit.framework.TestCase;

import org.approvaltests.legacycode.Range;
import org.lambda.functions.implementations.F1;
import org.lambda.query.Query;

public class EfficiencyTest extends TestCase
{
  public void testFastLambdas() throws Exception
  {
    int times = 1000000;
    long diff = doManyTimes(times);
    String time = MessageFormat.format("Time for {0} runs = {1}ms", times, diff);
    System.out.println(time);
    assertTrue(time, diff < 1400);
  }
  public long doManyTimes(int times)
  {
    long start = System.currentTimeMillis();
    final Integer matching = 18;
    Integer[] r = Range.get(0, times);
    Query.where(r, new F1<Integer, Boolean>(0, matching)
    {
      {
        ret(a == matching);
      }
    });
    long end = System.currentTimeMillis();
    long diff = end - start;
    return diff;
  }
}
