package org.approvaltests.writers;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalWriter;

import java.io.File;

public class DirectoryToDirectoryWriter implements ApprovalWriter
{
  private final File recieved;
  private final File goldMasterDirectory;
  public DirectoryToDirectoryWriter(File received, File goldMasterDirectory)
  {
    this.recieved = received;
    this.goldMasterDirectory = goldMasterDirectory;
  }

  @Override
  public File writeReceivedFile(File received)
  {
    return this.recieved;
  }

  @Override
  public String getFileExtensionWithDot()
  {
    return FileUtils.getExtensionWithDot(recieved.getAbsolutePath());
  }
}
