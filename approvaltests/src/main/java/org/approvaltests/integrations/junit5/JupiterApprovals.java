package org.approvaltests.integrations.junit5;

import com.spun.util.logger.SimpleLogger;
import org.approvaltests.SafetyCheckBeforeVerify;
import org.approvaltests.Approvals;
import org.approvaltests.core.Experimental;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.DynamicTest;
import org.lambda.actions.Action1;

public class JupiterApprovals
{
  @Experimental
  public static DynamicTest dynamicTest(String displayName, Action1<Options> action1)
  {
    Options options = Approvals.NAMES.withParameters(convertToLegalFileName(displayName));
    return DynamicTest.dynamicTest(displayName, () -> {
      try
      {
        SafetyCheckBeforeVerify.add((__, o) -> checkOptionsWasUsed(o, options));
        action1.call(options);
      }
      finally
      {
        SafetyCheckBeforeVerify.clear();
      }
    });
  }
  private static void checkOptionsWasUsed(Options actual, Options expected)
  {
    String actualAdditionalInformation = actual.forFile().getNamer().getAdditionalInformation();
    String expectedAdditionalInformation = expected.forFile().getNamer().getAdditionalInformation();
    if (!actualAdditionalInformation.startsWith(expectedAdditionalInformation))
    {
      SimpleLogger.variable("Actual additional information", actualAdditionalInformation);
      SimpleLogger.variable("Expected additional information", expectedAdditionalInformation);
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
