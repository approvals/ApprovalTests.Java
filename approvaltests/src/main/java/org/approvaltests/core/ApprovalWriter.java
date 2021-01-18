package org.approvaltests.core;

import java.io.File;

public interface ApprovalWriter
{
  public File writeReceivedFile(File received);
  public String getReceivedFilename(String base);
  public String getApprovalFilename(String base);
  public String getFileExtensionWithDot();
}
