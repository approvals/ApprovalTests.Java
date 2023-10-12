package org.approvaltests.integrations.junit5;

import org.approvaltests.Approvals;
import org.approvaltests.core.Experimental;
import org.approvaltests.core.Options;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.junit.jupiter.api.DynamicTest;
import org.lambda.actions.Action0;
import org.lambda.actions.Action1;

public class JupiterApprovals
{
  @Experimental
  public static DynamicTest dynamicTest(String displayName, Action1<Options> action1)
  {
    Options options = Approvals.NAMES.withParameters(convertToLegalFileName(displayName));
    return DynamicTest.dynamicTest(displayName, () -> {
      action1.call(options);
    });
  }
  public static String convertToLegalFileName(String uri)
  {
    return uri.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
  }
}
