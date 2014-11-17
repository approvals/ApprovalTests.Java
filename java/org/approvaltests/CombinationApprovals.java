package org.approvaltests;

import org.approvaltests.legacycode.LegacyApprovals;

public class CombinationApprovals
{
  public static void verifyAllCombinations(Object call, String method, Object[]... parametersVariations)
      throws Exception
  {
    LegacyApprovals.LockDown(call, method, parametersVariations);
  }
}
