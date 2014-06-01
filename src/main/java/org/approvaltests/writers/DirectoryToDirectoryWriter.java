package org.approvaltests.writers;

import java.io.File;

import org.approvaltests.core.ApprovalWriter;

public class DirectoryToDirectoryWriter implements ApprovalWriter
{
  private final File recieved;
  private final File goldMasterDirectory;
  public DirectoryToDirectoryWriter(File recieved, File goldMasterDirectory)
  {
    this.recieved = recieved;
    this.goldMasterDirectory = goldMasterDirectory;
  }
  @Override
  public String writeReceivedFile(String received) throws Exception
  {
    return this.recieved.getAbsolutePath();
  }
  @Override
  public String getReceivedFilename(String base)
  {
    return recieved.getAbsolutePath();
  }
  @Override
  public String getApprovalFilename(String base)
  {
    return goldMasterDirectory.getAbsolutePath() + File.separator + recieved.getName();
  }
}