package org.approvaltests.writers;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.core.Options;

import java.io.File;

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
    this(text, new Options().forFile().withExtension(fileExtensionWithoutDot));
  }
  public ApprovalTextWriter(String text, Options options)
  {
    this.text = options.scrub(text);
    this.fileExtensionWithoutDot = options.forFile().getFileExtension().substring(1);
  }
  public static ApprovalWriterFactory getFactory()
  {
    return (c, o) -> new ApprovalTextWriter("" + c, o);
  }
  @Override
  public File writeReceivedFile(File received)
  {
    FileUtils.writeFile(received, text);
    return received;
  }
  @Override
  public String getFileExtensionWithDot()
  {
    return "." + fileExtensionWithoutDot;
  }
}
