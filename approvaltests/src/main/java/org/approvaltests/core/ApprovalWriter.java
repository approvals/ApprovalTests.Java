package org.approvaltests.core;

import java.io.File;

public interface ApprovalWriter
{
  public File writeReceivedFile(File received);
  public String getFileExtensionWithDot();
}
