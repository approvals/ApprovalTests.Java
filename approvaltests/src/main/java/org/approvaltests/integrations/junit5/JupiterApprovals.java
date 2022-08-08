package org.approvaltests.integrations.junit5;

import org.approvaltests.core.Experimental;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.junit.jupiter.api.DynamicTest;
import org.lambda.actions.Action0;

public class JupiterApprovals
{
  @Experimental
  public static DynamicTest dynamicTest(String displayName, Action0 action0)
  {
    return DynamicTest.dynamicTest(displayName, () -> {
      try (NamedEnvironment en = NamerFactory.withParameters(convertToLegalFileName(displayName)))
      {
        action0.call();
      }
    });
  }
  public static String convertToLegalFileName(String uri)
  {
    return uri.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
  }
}
