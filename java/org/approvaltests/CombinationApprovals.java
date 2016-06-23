package org.approvaltests;

import java.util.Arrays;

import org.approvaltests.legacycode.LegacyApprovals;
import org.lambda.functions.Function2;

public class CombinationApprovals
{
  public static void verifyAllCombinations(Object call, String method, Object[]... parametersVariations)
      throws Exception
  {
    LegacyApprovals.LockDown(call, method, parametersVariations);
  }
  public static <IN1, IN2, OUT> void verifyAllCombinations(Function2<IN1, IN2, OUT> call, IN1[] parameters1,
      IN2[] parameters2) throws Exception
  {
    StringBuffer output = new StringBuffer();
    for (IN1 in1 : parameters1)
    {
      for (IN2 in2 : parameters2)
      {
        output.append(String.format("%s => %s \r\n", Arrays.asList(in1, in2), call.call(in1, in2)));
      }
    }
    Approvals.verify(output);
  }
}
