package org.approvaltests.namer;

import java.io.File;

public interface ApprovalNamer extends GetApprovalName, GetSourceFilePath
{
  public File getApprovedFile(String extensionWithDot);

  public File getReceivedFile(String extensionWithDot);

  /**
   * @deprecated Use {@code NamerUtils.getApprovalName(namer)}. This method will be removed from ApprovalNamer.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/Upgrade.V31.2.0.to.V31.3.0.md">Upgrade from 31.2.0 to 31.3.0</a>
   */
  @Deprecated
  @Override
  public String getApprovalName();

  /**
   * @deprecated Use {@code NamerUtils.getSourcePath(namer)}. This method will be removed from ApprovalNamer.
   *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/Upgrade.V31.2.0.to.V31.3.0.md">Upgrade from 31.2.0 to 31.3.0</a>
   */
  @Deprecated
  @Override
  public String getSourceFilePath();
}
