package org.approvaltests.core;

public interface ApprovalWriter
{
  public String writeReceivedFile(String received);
  public String getReceivedFilename(String base);
  public String getApprovalFilename(String base);
  public String getFileExtensionWithDot();
}
