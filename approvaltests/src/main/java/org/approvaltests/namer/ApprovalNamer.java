package org.approvaltests.namer;

import java.io.File;

public interface ApprovalNamer
{
  public File getApprovedFile(String extensionWithDot);

  public File getReceivedFile(String extensionWithDot);
}
