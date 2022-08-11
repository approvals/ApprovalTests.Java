package org.approvaltests.namer;

import com.spun.util.SystemUtils;
import org.approvaltests.core.Options;

public class NamerFactoryForOptions
{
  public static final NamerFactoryForOptions INSTANCE = new NamerFactoryForOptions();
  private NamerFactoryForOptions()
  {
  }
  public Options asOsSpecificTest()
  {
    return asOsSpecificTest(new Options());
  }
  public Options asOsSpecificTest(Options options)
  {
    return options.forFile().withAdditionalInformation(new OsEnvironmentLabeller().call());
  }
  public Options asMachineNameSpecificTest()
  {
    return asMachineNameSpecificTest(new Options());
  }
  public Options asMachineNameSpecificTest(Options options)
  {
    return options.forFile().withAdditionalInformation(SystemUtils.getComputerName());
  }
  public Options withParameters(String... parameters) {
    return withParameters(new Options(), parameters);
  }
  public Options withParameters(Options options, String... parameters) {
    for (String parameter : parameters) {
      options = options.forFile().withAdditionalInformation(parameter);
    }
    return options;
  }
}
