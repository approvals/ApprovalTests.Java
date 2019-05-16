package org.approvaltests.combinations;

import java.util.ArrayList;
import java.util.List;

import org.approvaltests.Approvals;
import org.approvaltests.legacycode.LegacyApprovals;
import org.lambda.functions.Function1;
import org.lambda.functions.Function2;
import org.lambda.functions.Function3;
import org.lambda.functions.Function4;
import org.lambda.functions.Function5;
import org.lambda.functions.Function6;
import org.lambda.functions.Function7;
import org.lambda.functions.Function8;
import org.lambda.functions.Function9;

public class CombinationApprovals
{
  private static final Object EMPTY_ENTRY = new Object();
  private static final Object EMPTY[]     = {EMPTY_ENTRY};
  public static void verifyAllCombinations(Object call, String method, Object[]... parametersVariations)
  {
    LegacyApprovals.LockDown(call, method, parametersVariations);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, OUT> void verifyAllCombinations(Function1<IN1, OUT> call, IN1[] parameters1)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1), parameters1, EMPTY, EMPTY, EMPTY,
        EMPTY, EMPTY, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, OUT> void verifyAllCombinations(Function2<IN1, IN2, OUT> call, IN1[] parameters1,
      IN2[] parameters2)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2), parameters1, parameters2,
        EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, OUT> void verifyAllCombinations(Function3<IN1, IN2, IN3, OUT> call,
      IN1[] parameters1, IN2[] parameters2, IN3[] parameters3)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3), parameters1, parameters2,
        parameters3, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, OUT> void verifyAllCombinations(Function4<IN1, IN2, IN3, IN4, OUT> call,
      IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4), parameters1,
        parameters2, parameters3, parameters4, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, OUT> void verifyAllCombinations(
      Function5<IN1, IN2, IN3, IN4, IN5, OUT> call, IN1[] parameters1, IN2[] parameters2, IN3[] parameters3,
      IN4[] parameters4, IN5[] parameters5)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5), parameters1,
        parameters2, parameters3, parameters4, parameters5, EMPTY, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, OUT> void verifyAllCombinations(
      Function6<IN1, IN2, IN3, IN4, IN5, IN6, OUT> call, IN1[] parameters1, IN2[] parameters2, IN3[] parameters3,
      IN4[] parameters4, IN5[] parameters5, IN6[] parameters6)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6), parameters1,
        parameters2, parameters3, parameters4, parameters5, parameters6, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, OUT> void verifyAllCombinations(
      Function7<IN1, IN2, IN3, IN4, IN5, IN6, IN7, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6, n7),
        parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7, EMPTY, EMPTY);
  }
  /**
   * /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, OUT> void verifyAllCombinations(
      Function8<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
      IN8[] parameters8)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6, n7, n8),
        parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7, parameters8,
        EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> void verifyAllCombinations(
      Function9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
      IN8[] parameters8, IN9[] parameters9)
  {
    StringBuffer output = new StringBuffer();
    for (IN1 in1 : parameters1)
    {
      for (IN2 in2 : parameters2)
      {
        for (IN3 in3 : parameters3)
        {
          for (IN4 in4 : parameters4)
          {
            for (IN5 in5 : parameters5)
            {
              for (IN6 in6 : parameters6)
              {
                for (IN7 in7 : parameters7)
                {
                  for (IN8 in8 : parameters8)
                  {
                    for (IN9 in9 : parameters9)
                    {
                      String result;
                      try
                      {
                        result = "" + call.call(in1, in2, in3, in4, in5, in6, in7, in8, in9);
                      }
                      catch (SkipCombination e)
                      {
                        continue;
                      }
                      catch (Throwable e)
                      {
                        result = e.getMessage();
                      }
                      output.append(String.format("%s => %s \n",
                          extracted(in1, in2, in3, in4, in5, in6, in7, in8, in9), result));
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    Approvals.verify(output);
  }
  private static List<Object> extracted(Object... objects)
  {
    List<Object> list = new ArrayList<Object>();
    for (Object object : objects)
    {
      if (object != EMPTY_ENTRY)
      {
        list.add(object);
      }
    }
    return list;
  }
}
