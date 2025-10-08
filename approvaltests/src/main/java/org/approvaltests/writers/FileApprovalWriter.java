package org.approvaltests.writers;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalWriter;

import java.io.File;

public class FileApprovalWriter implements ApprovalWriter
{
  private final File newFile;
  private String     extensionWithDot;
  public FileApprovalWriter(File newFile)
  {
    this.newFile = newFile;
    extensionWithDot = FileUtils.getExtensionWithDot(newFile.getName());
  }

  @Override
  public File writeReceivedFile(File received)
  {
    return newFile;
  }

  @Override
  public String getFileExtensionWithDot()
  {
    return extensionWithDot;
  }
}
