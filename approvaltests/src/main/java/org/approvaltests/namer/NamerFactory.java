package org.approvaltests.namer;

import org.approvaltests.core.ApprovalTestPackageSettings;
import org.packagesettings.PackageLevelSettings;

public class NamerFactory
{
  public static String getSubdirectory()
  {
    return PackageLevelSettings.getValueFor(ApprovalTestPackageSettings.USE_APPROVAL_SUBDIRECTORY);
  }

  public static String getApprovalBaseDirectory()
  {
    return PackageLevelSettings.getValueFor(ApprovalTestPackageSettings.APPROVAL_BASE_DIRECTORY);
  }
}
