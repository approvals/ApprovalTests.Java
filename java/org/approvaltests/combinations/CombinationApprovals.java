package org.approvaltests.combinations;

import java.util.Arrays;

import org.approvaltests.Approvals;
import org.approvaltests.legacycode.LegacyApprovals;
import org.lambda.functions.Function2;

public class CombinationApprovals
{
  public static void verifyAllCombinations(Object call, String method, Object[]... parametersVariations)
      throws Exception
  {
    LegacyApprovals.LockDown(call, method, parametersVariations);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, OUT> void verifyAllCombinations(Function2<IN1, IN2, OUT> call, IN1[] parameters1,
      IN2[] parameters2) throws Exception
  {
    StringBuffer output = new StringBuffer();
    for (IN1 in1 : parameters1)
    {
      for (IN2 in2 : parameters2)
      {
        String result;
        try
        {
          result = "" + call.call(in1, in2);
        }
        catch (SkipCombination e)
        {
          continue;
        }
        catch (Throwable e)
        {
          result = e.getMessage();
        }
        output.append(String.format("%s => %s \r\n", Arrays.asList(in1, in2), result));
      }
    }
    Approvals.verify(output);
  }
}
