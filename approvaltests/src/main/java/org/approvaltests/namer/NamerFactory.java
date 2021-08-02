package org.approvaltests.namer;

import org.approvaltests.core.ApprovalTestPackageSettings;
import org.lambda.functions.Function0;
import org.packagesettings.PackageLevelSettings;

import com.spun.util.StringUtils;
import com.spun.util.SystemUtils;

public class NamerFactory
{
  public static String additionalInformation;
  public static String getAndClearAdditionalInformation()
  {
    String result = getAdditionalInformation();
    additionalInformation = null;
    return result;
  }
  public static String getAdditionalInformation()
  {
    if (additionalInformation == null)
    { return ""; }
    return "." + additionalInformation;
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
    return PackageLevelSettings.getValueFor(ApprovalTestPackageSettings.USE_APPROVAL_SUBDIRECTORY);
  }
  public static String getApprovalBaseDirectory()
  {
    return PackageLevelSettings.getValueFor(ApprovalTestPackageSettings.APPROVAL_BASE_DIRECTORY);
  }
  public static NamedEnvironment withParameters(Object... parameters)
  {
    return new NamedEnvironment(StringUtils.join(parameters, ".", o -> "" + o));
  }
  public static boolean isEmpty()
  {
    return additionalInformation == null;
  }
}
