package org.approvaltests.namer;

import org.lambda.functions.Function0;

public class NamerFactory
{
  public static String additionalInformation;
  public static String getAndClearAdditionalInformation()
  {
    if (additionalInformation == null) { return ""; }
    String out = "." + additionalInformation;
    additionalInformation = null;
    return out;
  }
  public static ApprovalResults ApprovalResults = new ApprovalResults();
  public static NamedEnvironment asMachineSpecificTest(Function0<String> environmentLabeller)
  {
    return new NamedEnvironment(environmentLabeller.call());
  }
  public static NamedEnvironment asOsSpecificTest()
  {
    return asMachineSpecificTest(new OsEnvironmentLabeller());
  }
}
