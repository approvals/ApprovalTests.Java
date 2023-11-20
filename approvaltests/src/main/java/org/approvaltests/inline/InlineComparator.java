package org.approvaltests.inline;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.Options;
import org.approvaltests.namer.ApprovalNamer;

import java.io.File;
import java.io.IOException;

import static org.approvaltests.writers.Writer.approved;
import static org.approvaltests.writers.Writer.received;

public class InlineComparator implements ApprovalNamer
{
  private ApprovalFailureReporter reporter = null;
  private final String            expected;
  private File                    approvedFile;
  private File                    receivedFile;
  public InlineComparator(String expected, ApprovalFailureReporter reporter)
  {
    this.expected = expected;
    if (reporter != null)
    {
      this.reporter = new InlineJavaReporter(reporter);
    }
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
    return "";
  }
  public boolean report(String received, String approved)
  {
    return reporter.report(received, approved);
  }
  public Options setForOptions(Options options)
  {
    if (reporter != null)
    {
      options = options.withReporter(reporter);
    }
    return options.forFile().withNamer(this);
  }
}
