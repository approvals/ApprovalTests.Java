package org.approvaltests.namer;

import org.approvaltests.core.Options;

public class NamerFactoryForOptions
{
  public static final NamerFactoryForOptions INSTANCE = new NamerFactoryForOptions();
  private NamerFactoryForOptions()
  {
  }
  public Options asOsSpecificTest()
  {
    return new Options().forFile().withAdditionalInformation(new OsEnvironmentLabeller().call());
  }
}
