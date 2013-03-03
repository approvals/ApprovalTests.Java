package org.approvaltests.writers;

import java.io.File;

import org.approvaltests.core.ApprovalWriter;

import com.spun.util.io.FileUtils;

public class ApprovalTextWriter implements ApprovalWriter
{
  private final String text;
  private final String fileExtensionWithoutDot;
  public ApprovalTextWriter(String text, String fileExtensionWithoutDot)
  {
    this.text = text;
    this.fileExtensionWithoutDot = fileExtensionWithoutDot;
  }
  @Override
  public String writeReceivedFile(String received) throws Exception
  {
    FileUtils.writeFile(new File(received), text);
    return received;
  }
  @Override
  public String getApprovalFilename(String base)
  {
    return base + Writer.approved + "."+ fileExtensionWithoutDot;
  }
  @Override
  public String getReceivedFilename(String base)
  {
    return base + Writer.received + "."+fileExtensionWithoutDot;
  }
}
