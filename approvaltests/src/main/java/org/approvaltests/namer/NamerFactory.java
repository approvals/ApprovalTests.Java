package org.approvaltests.namer;

import com.spun.util.SystemUtils;
import org.lambda.functions.Function0;
import org.packagesettings.PackageLevelSettings;

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
    return asMachineSpecificTest(environmentLabeller.call());
  }
  public static NamedEnvironment asMachineNameSpecificTest()
  {
    return asMachineSpecificTest(SystemUtils.getComputerName());
  }
  public static NamedEnvironment asOsSpecificTest()
  {
    return asMachineSpecificTest(new OsEnvironmentLabeller());
  }
  public static NamedEnvironment asMachineSpecificTest(String environmentName)
  {
    return new NamedEnvironment(environmentName);
  }
  public static MultipleFilesLabeller useMultipleFiles()
  {
    return new MultipleFilesLabeller();
  }
  public static String getSubdirectory()
  {
    return (String) PackageLevelSettings.getValueFor("UseApprovalSubdirectory");
  }
  public static String getApprovalBaseDirectory()
  {
    return (String) PackageLevelSettings.getValueFor("ApprovalBaseDirectory");
  }
}
