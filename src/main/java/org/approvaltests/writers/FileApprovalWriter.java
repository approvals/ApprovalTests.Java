package org.approvaltests.writers;

import java.io.File;

import org.approvaltests.core.ApprovalWriter;

public class FileApprovalWriter implements ApprovalWriter
{
  private final File newFile;
  public FileApprovalWriter(File newFile)
  {
    this.newFile = newFile;
  }
  @Override
  public String writeReceivedFile(String received) throws Exception
  {
    return newFile.getAbsolutePath();
  }
  @Override
  public String getApprovalFilename(String base)
  {
    return base + Writer.approved + ".html";
  }
  @Override
  public String getReceivedFilename(String base)
  {
    return base + Writer.received + ".html";
  }
}
