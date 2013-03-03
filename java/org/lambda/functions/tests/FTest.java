package org.lambda.functions.tests;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;
import org.lambda.functions.implementations.F0;
import org.lambda.functions.implementations.F3;
import org.lambda.functions.implementations.F4;
import org.lambda.functions.implementations.F5;
import org.lambda.functions.implementations.F6;
import org.lambda.functions.implementations.F7;
import org.lambda.functions.implementations.F8;
import org.lambda.functions.implementations.F9;

@UseReporter(TortoiseTextDiffReporter.class)
public class FTest extends TestCase
{
  public void test9() throws Exception
  {
    final Integer pre = 100;
    Approvals.verifyAll(
        "",
        next9(0,
            new F9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer[]>(0,
                0, 0, 0, 0, 0, 0, 0, 0, pre)
            {
              {
                ret(new Integer[]{pre + a, pre + b, pre + c, pre + d, pre + e, pre + f, pre + g, pre + h, pre + i});
              }
            }));
  }
  private <Out> Out next9(int i,
      F9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Out> f9)
  {
    return f9.call(i + 1, i + 2, i + 3, i + 4, i + 5, i + 6, i + 7, i + 8, i + 9);
  }
  public void test8() throws Exception
  {
    final Integer pre = 100;
    Integer total = 101 + 2 + 3 + 4 + 5 + 6 + 7 + 8;
    assertEquals(
        total,
        next8(0, new F8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>(1, 2, 3,
            4, 5, 6, 7, 8, pre)
        {
          {
            ret(pre + a + b + c + d + e + f + g + h);
          }
        }));
  }
  private <Out> Out next8(int i, F8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Out> f)
  {
    return f.call(i + 1, i + 2, i + 3, i + 4, i + 5, i + 6, i + 7, i + 8);
  }
  public void test7() throws Exception
  {
    final Integer pre = 100;
    Integer total = 101 + 2 + 3 + 4 + 5 + 6 + 7;
    assertEquals(
        total,
        next7(0, new F7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>(1, 2, 3, 4, 5, 6,
            7, pre)
        {
          {
            ret(pre + a + b + c + d + e + f + g);
          }
        }));
  }
  private <Out> Out next7(int i, F7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Out> f)
  {
    return f.call(i + 1, i + 2, i + 3, i + 4, i + 5, i + 6, i + 7);
  }
  public void test6() throws Exception
  {
    final Integer pre = 100;
    Integer total = 101 + 2 + 3 + 4 + 5 + 6;
    assertEquals(total,
        next6(0, new F6<Integer, Integer, Integer, Integer, Integer, Integer, Integer>(1, 2, 3, 4, 5, 6, pre)
        {
          {
            ret(pre + a + b + c + d + e + f);
          }
        }));
  }
  private <Out> Out next6(int i, F6<Integer, Integer, Integer, Integer, Integer, Integer, Out> f)
  {
    return f.call(i + 1, i + 2, i + 3, i + 4, i + 5, i + 6);
  }
  public void test5() throws Exception
  {
    final Integer pre = 100;
    Integer total = 101 + 2 + 3 + 4 + 5;
    assertEquals(total, next5(0, new F5<Integer, Integer, Integer, Integer, Integer, Integer>(1, 2, 3, 4, 5, pre)
    {
      {
        ret(pre + a + b + c + d + e);
      }
    }));
  }
  private <Out> Out next5(int i, F5<Integer, Integer, Integer, Integer, Integer, Out> f)
  {
    return f.call(i + 1, i + 2, i + 3, i + 4, i + 5);
  }
  public void test4() throws Exception
  {
    final Integer pre = 100;
    Integer total = 101 + 2 + 3 + 4;
    assertEquals(total, next4(0, new F4<Integer, Integer, Integer, Integer, Integer>(1, 2, 3, 4, pre)
    {
      {
        ret(pre + a + b + c + d);
      }
    }));
  }
  private <Out> Out next4(int i, F4<Integer, Integer, Integer, Integer, Out> f)
  {
    return f.call(i + 1, i + 2, i + 3, i + 4);
  }
  public void test3() throws Exception
  {
    final Integer pre = 100;
    Integer total = 101 + 2 + 3;
    assertEquals(total, next3(0, new F3<Integer, Integer, Integer, Integer>(1, 2, 3, pre)
    {
      {
        ret(pre + a + b + c);
      }
    }));
  }
  private <Out> Out next3(int i, F3<Integer, Integer, Integer, Out> f)
  {
    return f.call(i + 1, i + 2, i + 3);
  }
  public void test0() throws Exception
  {
    final Integer pre = 100;
    Integer total = 100;
    assertEquals(total, next0(0, new F0<Integer>(pre)
    {
      {
        ret(pre);
      }
    }));
  }
  private <Out> Out next0(int i, F0<Out> f)
  {
    return f.call();
  }
}
