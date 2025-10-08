package org.approvaltests.namer;

import com.spun.util.logger.SimpleLogger;

import java.util.Arrays;

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

  public boolean isCurrentEnvironmentValidFor(String... environment)
  {
    return isCurrentEnvironmentValidFor(true, environment);
  }

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
