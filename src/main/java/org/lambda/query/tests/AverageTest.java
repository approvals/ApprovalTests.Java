package org.lambda.query.tests;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.lambda.functions.implementations.F1;
import org.lambda.query.Query;

public class AverageTest extends TestCase
{
  public void testAverage() throws Exception
  {
     List<Integer> numbers = Arrays.asList(3,5,7,9);
    int a = 0;
    assertEquals(6, Query.average(numbers,
        new F1<Integer, Number>(a){{ret(a);}}),0.00);
  }
  public void testSum() throws Exception
  {
    List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9);
    assertEquals(5, Query.sum(numbers,
        new F1<Integer, Number>(0){{ret(a%2);}}),0.00);
    assertEquals(45, Query.sum(numbers),0.00);
  }
  
  public void testMaxAndMin() throws Exception
  {
    List<Integer> numbers = Arrays.asList(40,20,170,30);
    assertEquals(170, (int)Query.max(numbers));
    assertEquals(20, (int)Query.min(numbers));
  }
  

}
