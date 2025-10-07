package org.approvaltests.writers;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalWriter;

import java.io.File;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

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
  public String getFileExtensionWithDot()
  {
    return "." + fileExtension;
  }
}
