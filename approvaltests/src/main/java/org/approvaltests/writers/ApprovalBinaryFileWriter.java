package org.approvaltests.writers;

import java.io.File;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.approvaltests.core.ApprovalWriter;

import com.spun.util.io.FileUtils;

public class ApprovalBinaryFileWriter implements ApprovalWriter
{
  private CharSequence data;
  private String       fileExtension;
  private InputStream  dataStream;
  public ApprovalBinaryFileWriter(CharSequence charSequence, String fileExtensionWithoutDot)
  {
    this.data = charSequence;
    this.fileExtension = fileExtensionWithoutDot;
  }
  public ApprovalBinaryFileWriter(InputStream stream, String fileExtensionWithoutDot)
  {
    this.dataStream = stream;
    this.fileExtension = fileExtensionWithoutDot;
  }
  public ApprovalBinaryFileWriter(ReadableByteChannel stream, String fileExtensionWithoutDot)
  {
    this(Channels.newInputStream(stream), fileExtensionWithoutDot);
  }
  @Override
  public File writeReceivedFile(File received)
  {
    if (dataStream == null)
    {
      FileUtils.writeFile(received, data);
    }
    else
    {
      FileUtils.writeFile(received, dataStream);
    }
    return received;
  }
  @Override
  public String getApprovalFilename(String base)
  {
    return base + Writer.approved + "." + fileExtension;
  }
  @Override
  public String getReceivedFilename(String base)
  {
    return base + Writer.received + fileExtension;
  }
  @Override
  public String getFileExtensionWithDot()
  {
    return "." + fileExtension;
  }
}
