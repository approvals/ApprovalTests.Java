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
    if (Arrays.asList(environment).contains(NamerFactory.getAdditionalInformation()))
    {
      return true;
    }
    else
    {
      SimpleLogger.message(
          String.format("Not valid for current environment: \"%s\"", NamerFactory.getAdditionalInformation()));
      return false;
    }
  }
}
