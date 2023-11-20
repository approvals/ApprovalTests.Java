package org.approvaltests.inline;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.Options;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.StackTraceNamer;

import java.io.File;
import java.io.IOException;

import static org.approvaltests.writers.Writer.approved;
import static org.approvaltests.writers.Writer.received;

public class InlineComparator implements ApprovalNamer, ApprovalFailureReporter
{
  private final InlineJavaReporter inlineJavaReporter;
  private String                        expected;
  private File                          approvedFile;
  private File                          receivedFile;
  public InlineComparator(String expected, ApprovalFailureReporter reporter)
  {
    this.expected = expected;
    inlineJavaReporter = new InlineJavaReporter(reporter);
  }
  @Override
  public File getApprovedFile(String extensionWithDot)
  {
    if (approvedFile == null)
    {
      try
      {
        this.approvedFile = File.createTempFile("temp", approved + extensionWithDot);
        FileUtils.writeFile(approvedFile, expected);
      }
      catch (IOException e)
      {
        throw new RuntimeException(e);
      }
    }
    return approvedFile;
  }
  @Override
  public File getReceivedFile(String extensionWithDot)
  {
    if (receivedFile == null)
    {
      try
      {
        this.receivedFile = File.createTempFile("temp", received + extensionWithDot);
      }
      catch (IOException e)
      {
        throw new RuntimeException(e);
      }
    }
    return receivedFile;
  }
  @Override
  public ApprovalNamer addAdditionalInformation(String info)
  {
    return this;
  }
  @Override
  public String getApprovalName()
  {
    return "";
  }
  @Override
  public String getSourceFilePath()
  {
    return inlineJavaReporter.getSourceFilePath();
  }
  @Override
  public boolean report(String received, String approved)
  {
    return inlineJavaReporter.report(received, approved);
  }
  private String createReceived(String actual)
  {
    return inlineJavaReporter.createReceived(actual);
  }

  public Options setForOptions(Options options)
  {
    if (inlineJavaReporter.reporter != null)
    {
      options = options.withReporter(this);
    }
    return options//
        .forFile().withNamer(this);
  }
}
