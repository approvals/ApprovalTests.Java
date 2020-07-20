package org.approvaltests.writers;

import java.io.File;

import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.core.Options;

import com.spun.util.io.FileUtils;

public class ApprovalTextWriter implements ApprovalWriter
{
  private final String text;
  private final String fileExtensionWithoutDot;

  /**
   * @deprecated Use {@link #ApprovalTextWriter(String, Options)} instead.
   */
  @Deprecated
  public ApprovalTextWriter(String text, String fileExtensionWithoutDot)
  {
    this(text, new Options(), fileExtensionWithoutDot);
  }
  public ApprovalTextWriter(String text, Options options)
  {
    this(text, options, "txt");
  }
  public ApprovalTextWriter(String text, Options options, String fileExtensionWithoutDot)
  {
    this.text = options.scrub(text);
    this.fileExtensionWithoutDot = fileExtensionWithoutDot;
  }
  @Override
  public String writeReceivedFile(String received)
  {
    FileUtils.writeFile(new File(received), text);
    return received;
  }
  @Override
  public String getApprovalFilename(String base)
  {
    return base + Writer.approved + "." + fileExtensionWithoutDot;
  }
  @Override
  public String getReceivedFilename(String base)
  {
    return base + Writer.received + "." + fileExtensionWithoutDot;
  }
}
