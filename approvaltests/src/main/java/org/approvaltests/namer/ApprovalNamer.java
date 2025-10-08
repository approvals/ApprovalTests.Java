package org.approvaltests.namer;

import java.io.File;

public interface ApprovalNamer extends GetApprovalName, GetSourceFilePath
{
  public File getApprovedFile(String extensionWithDot);

  public File getReceivedFile(String extensionWithDot);

  public ApprovalNamer addAdditionalInformation(String info);

  String getAdditionalInformation();
}
