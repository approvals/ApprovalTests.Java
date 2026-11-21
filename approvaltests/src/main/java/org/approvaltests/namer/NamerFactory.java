package org.approvaltests.namer;

import com.spun.util.StringUtils;
import com.spun.util.SystemUtils;
import org.approvaltests.core.ApprovalTestPackageSettings;
import org.lambda.functions.Function0;
import org.packagesettings.PackageLevelSettings;

public class NamerFactory
{
  private static final ThreadLocal<String> additionalInformation = new ThreadLocal<>();
  /**
   * @deprecated Additional information now lives on {@link org.approvaltests.core.Options}. Read it with
   *             {@code options.forFile().getAdditionalInformation()}.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
   */
  @Deprecated
  public static String getAndClearAdditionalInformation()
  {
    String result = getAdditionalInformation();
    additionalInformation.set(null);
    return result;
  }

  /**
   * @deprecated Additional information now lives on {@link org.approvaltests.core.Options}. Read it with
   *             {@code options.forFile().getAdditionalInformation()}.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
   */
  @Deprecated
  public static String getAdditionalInformation()
  {
    if (additionalInformation.get() == null)
    { return ""; }
    return "." + additionalInformation.get();
  }
  /**
   * @deprecated Use {@code Approvals.NAMES.useMultipleFiles()} and pass {@code counter.next()} to each verify.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
   */
  @Deprecated
  public static ApprovalResults ApprovalResults = new ApprovalResults();
  public static NamedEnvironment asMachineSpecificTest(Function0<String> environmentLabeller)
  {
    return asMachineSpecificTest(environmentLabeller.call());
  }

  /**
   * @deprecated Use {@code Approvals.verify(result, Approvals.NAMES.asMachineNameSpecificTest())} instead.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
   */
  @Deprecated
  public static NamedEnvironment asMachineNameSpecificTest()
  {
    return asMachineSpecificTest(SystemUtils.getComputerName());
  }

  /**
   * @deprecated Use {@code Approvals.verify(result, Approvals.NAMES.asOsSpecificTest())} instead.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
   */
  @Deprecated
  public static NamedEnvironment asOsSpecificTest()
  {
    return asMachineSpecificTest(new OsEnvironmentLabeller());
  }

  /**
   * @deprecated Use {@code Approvals.verify(result, new Options().forFile().withAdditionalInformation(environmentName))} instead.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
   */
  @Deprecated
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

  /**
   * @deprecated Use {@code Approvals.verify(result, Approvals.NAMES.withParameters("a", "b"))} instead.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
   */
  @Deprecated
  public static NamedEnvironment withParameters(Object... parameters)
  {
    return new NamedEnvironment(StringUtils.join(parameters, ".", o -> "" + o));
  }

  /**
   * @deprecated Check {@code options.forFile().getAdditionalInformation().isEmpty()} instead.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
   */
  @Deprecated
  public static boolean isEmpty()
  {
    return additionalInformation.get() == null;
  }

  /**
   * @deprecated Use {@code new Options().forFile().withAdditionalInformation(info)} and pass the Options to verify.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
   */
  @Deprecated
  public static void setAdditionalInformation(String info)
  {
    additionalInformation.set(info);
  }
}
