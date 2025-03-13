package org.approvaltests.integrations.junit5;

import org.approvaltests.SafetyCheckBeforeVerify;
import org.approvaltests.Approvals;
import org.approvaltests.core.Experimental;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.DynamicTest;
import org.lambda.actions.Action1;

public class JupiterApprovals
{
  @Experimental
  public static DynamicTest dynamicTest(String displayName, Action1<Options> action1)
  {
    Options options = Approvals.NAMES.withParameters(convertToLegalFileName(displayName));
    return DynamicTest.dynamicTest(displayName, () -> {
      SafetyCheckBeforeVerify.add((__, o) -> checkOptionsWasUsed(o, options));
      action1.call(options);
    });
  }
  private static void checkOptionsWasUsed(Options actual, Options expected)
  {
    if (!actual.forFile().getNamer().getAdditionalInformation()
        .startsWith(expected.forFile().getNamer().getAdditionalInformation()))
    {
      String helpMessage = "When using dynamic tests and Approvals, all calls to verify() must use the original Options or a derivative:  \n"
          + "   wrong: o -> Approvals.verify(result);  \n" + "   right: o -> Approvals.verify(result, o);  \n"
          + " More at: https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UseTestFactory.md";
      throw new RuntimeException(helpMessage);
    }
  }
  public static String convertToLegalFileName(String uri)
  {
    return uri.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
  }
}
