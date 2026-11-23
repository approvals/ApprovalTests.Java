package org.approvaltests.writers;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.core.Options;

import java.io.File;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ApprovalBinaryFileWriter implements ApprovalWriter
{
  private CharSequence data;
  private String       fileExtensionWithDot;
  private InputStream  dataStream;
  /**
   * @deprecated Use {@link #ApprovalBinaryFileWriter(CharSequence, Options)} instead.
   */
  @Deprecated
  public ApprovalBinaryFileWriter(CharSequence charSequence, String fileExtensionWithoutDot)
  {
    this(charSequence, new Options().forFile().withExtension(fileExtensionWithoutDot));
  }

  public ApprovalBinaryFileWriter(CharSequence charSequence, Options options)
  {
    this.data = charSequence;
    this.fileExtensionWithDot = options.forFile().getFileExtension();
  }

  /**
   * @deprecated Use {@link #ApprovalBinaryFileWriter(InputStream, Options)} instead.
   */
  @Deprecated
  public ApprovalBinaryFileWriter(InputStream stream, String fileExtensionWithoutDot)
  {
    this(stream, new Options().forFile().withExtension(fileExtensionWithoutDot));
  }

  public ApprovalBinaryFileWriter(InputStream stream, Options options)
  {
    this.dataStream = stream;
    this.fileExtensionWithDot = options.forFile().getFileExtension();
  }

  /**
   * @deprecated Use {@link #ApprovalBinaryFileWriter(ReadableByteChannel, Options)} instead.
   */
  @Deprecated
  public ApprovalBinaryFileWriter(ReadableByteChannel stream, String fileExtensionWithoutDot)
  {
    this(stream, new Options().forFile().withExtension(fileExtensionWithoutDot));
  }

  public ApprovalBinaryFileWriter(ReadableByteChannel stream, Options options)
  {
    this(Channels.newInputStream(stream), options);
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
    return fileExtensionWithDot;
  }
}
