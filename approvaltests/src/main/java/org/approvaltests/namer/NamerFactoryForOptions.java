package org.approvaltests.namer;

import org.approvaltests.core.Options;

import java.io.File;

public class NamerFactoryForOptions
{
  public static final NamerFactoryForOptions INSTANCE = new NamerFactoryForOptions();
  private NamerFactoryForOptions()
  {
  }
  public Options asOsSpecificTest()
  {
    Options options = new Options();
    ApprovalNamer namer = options.forFile().getNamer();
    File approvedFile = namer.getApprovedFile("");
    String baseName = approvedFile.getName().substring(0, approvedFile.getName().length() - ".approved".length());
    String fileBaseName = baseName + "." + new OsEnvironmentLabeller().call();
    return options.forFile().withBaseName(fileBaseName);
  }
}
