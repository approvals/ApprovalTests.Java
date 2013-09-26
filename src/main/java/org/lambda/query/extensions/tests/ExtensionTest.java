package org.lambda.query.extensions.tests;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.lambda.functions.implementations.F1;
import org.lambda.query.extensions.Queryyy;

public class ExtensionTest extends TestCase
{
  @SuppressWarnings("unchecked")
  public void testHighestDivisorOf3() throws Exception
  {
    List<Integer> list = Arrays.asList(30, 46, 60);
    Approvals.verifyAll("", list.use(Queryyy.class).where(new F1<Integer, Boolean>(0)
    {
      {
        returnValue(a % 3 == 0);
      }
    }).use(Queryyy.class).select(new F1<Integer, String>(0)
    {
      {
        returnValue(Integer.toHexString(a));
      }
    }));
  }
}
