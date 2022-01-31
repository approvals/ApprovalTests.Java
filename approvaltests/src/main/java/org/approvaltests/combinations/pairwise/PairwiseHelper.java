package org.approvaltests.combinations.pairwise;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationsHelper;
import org.approvaltests.combinations.SkipCombination;
import org.approvaltests.core.Options;
import org.lambda.functions.Function9;

import java.util.List;

public class PairwiseHelper
{
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> void verifyBestCoveringPairs(
      Function9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
      IN8[] parameters8, IN9[] parameters9, Options options)
  {
    Pairwise pairwise = Pairwise.toPairWise(parameters1, parameters2, parameters3, parameters4, parameters5,
        parameters6, parameters7, parameters8, parameters9);
    final List<Case> cases = pairwise.getCases();
    StringBuffer output = new StringBuffer();
    int totalPossibleSize = pairwise.getTotalPossibleCombinations();
    output.append(String.format("Testing an optimized %s/%s scenarios:\n\n", cases.size(), totalPossibleSize));
    for (Case params : cases)
    {
      String result;
      final IN1 in1 = (IN1) params.get(0);
      final IN2 in2 = (IN2) params.get(1);
      final IN3 in3 = (IN3) params.get(2);
      final IN4 in4 = (IN4) params.get(3);
      final IN5 in5 = (IN5) params.get(4);
      final IN6 in6 = (IN6) params.get(5);
      final IN7 in7 = (IN7) params.get(6);
      final IN8 in8 = (IN8) params.get(7);
      final IN9 in9 = (IN9) params.get(8);
      try
      {
        result = "" + call.call(in1, in2, in3, in4, in5, in6, in7, in8, in9);
      }
      catch (SkipCombination e)
      {
        continue;
      }
      catch (Throwable t)
      {
        result = String.format("%s: %s", t.getClass().getName(), t.getMessage());
      }
      output.append(String.format("%s => %s \n",
          CombinationsHelper.filterEmpty(in1, in2, in3, in4, in5, in6, in7, in8, in9), result));
    }
    Approvals.verify(output, options);
  }
}
