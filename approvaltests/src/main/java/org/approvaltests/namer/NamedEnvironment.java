package org.approvaltests.namer;

import com.spun.util.logger.SimpleLogger;

import java.util.Arrays;

/**
 * @deprecated Pass {@link org.approvaltests.core.Options} to verify instead, for example
 *             {@code Approvals.verify(result, Approvals.NAMES.withParameters("a", "b"))}.
 *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
 */
@Deprecated
public class NamedEnvironment implements AutoCloseable
{
  public NamedEnvironment(String info)
  {
    NamerFactory.setAdditionalInformation(info);
  }

  @Override
  public void close()
  {
    NamerFactory.getAndClearAdditionalInformation();
  }

  /**
   * @deprecated Use your test framework's assumptions instead, for example
   *             {@code assumeTrue(SystemUtils.getComputerName().equals("my-machine"))}.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
   */
  @Deprecated
  public boolean isCurrentEnvironmentValidFor(String... environment)
  {
    return isCurrentEnvironmentValidFor(true, environment);
  }

  /**
   * @deprecated Use your test framework's assumptions instead, for example
   *             {@code assumeTrue(SystemUtils.getComputerName().equals("my-machine"))}.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
   */
  @Deprecated
  public boolean isCurrentEnvironmentValidFor(boolean displayMessage, String... environment)
  {
    if (Arrays.asList(environment).contains(NamerFactory.getAdditionalInformation()))
    {
      return true;
    }
    else
    {
      if (displayMessage)
      {
        SimpleLogger.message(
            String.format("Not valid for current environment: \"%s\"", NamerFactory.getAdditionalInformation()));
      }
      return false;
    }
  }
}
