package org.approvaltests.core;

import org.approvaltests.Approvals;
import org.approvaltests.ReporterFactory;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.NamerWrapper;
import org.approvaltests.scrubbers.NoOpScrubber;

import java.util.Objects;

public class Options
{
  private Scrubber                scrubber    = NoOpScrubber.INSTANCE;
  private ApprovalFailureReporter reporter    = ReporterFactory.get();
  private FileOptions             fileOptions = new FileOptions();
  public Options()
  {
  }
  public Options(Scrubber scrubber)
  {
    Objects.requireNonNull(scrubber, "need to provide a scrubber");
    this.scrubber = scrubber;
  }
  public Options(ApprovalFailureReporter reporter)
  {
    Objects.requireNonNull(reporter, "need to provide a reporter");
    this.reporter = reporter;
  }
  private Options(Scrubber scrubber, ApprovalFailureReporter reporter, FileOptions fileOptions)
  {
    this.scrubber = scrubber;
    this.reporter = reporter;
    this.fileOptions = fileOptions;
  }
  public Options(Options parent, FileOptions fileOptions)
  {
    this(parent.scrubber, parent.reporter, fileOptions);
  }
  public ApprovalFailureReporter getReporter()
  {
    return this.reporter;
  }
  public Options withReporter(ApprovalFailureReporter reporter)
  {
    Objects.requireNonNull(reporter, "need to provide a reporter");
    return new Options(this.scrubber, reporter, fileOptions);
  }
  public Options withScrubber(Scrubber scrubber)
  {
    Objects.requireNonNull(scrubber, "need to provide a scrubber");
    return new Options(scrubber, this.reporter, fileOptions);
  }
  public String scrub(String input)
  {
    return scrubber.scrub(input);
  }
  public FileOptions forFile()
  {
    fileOptions.setParent(this);
    return fileOptions;
  }
  public static class FileOptions
  {
    private String         fileExtension = ".txt";
    protected NamerWrapper approvalNamer = null;
    protected Options      parent;
    public FileOptions()
    {
    }
    public FileOptions(String fileExtension)
    {
      this.fileExtension = fileExtension;
    }
    public FileOptions(NamerWrapper approvalNamer, String fileExtension)
    {
      this.approvalNamer = approvalNamer;
      this.fileExtension = fileExtension;
    }
    public void setParent(Options parent)
    {
      this.parent = parent;
    }
    public Options withExtension(String fileExtensionWithDot)
    {
      if (!fileExtensionWithDot.startsWith("."))
      {
        fileExtensionWithDot = "." + fileExtensionWithDot;
      }
      FileOptions f = new FileOptions(this.approvalNamer, fileExtensionWithDot);
      return new Options(parent, f);
    }
    public ApprovalNamer getNamer()
    {
      return approvalNamer == null ? Approvals.createApprovalNamer() : approvalNamer;
    }
    public String getFileExtension()
    {
      return fileExtension;
    }
    public Options withBaseName(String fileBaseName)
    {
      NamerWrapper approvalNamer = new NamerWrapper(() -> fileBaseName, getNamer());
      FileOptions f = new FileOptions(approvalNamer, this.fileExtension);
      return new Options(parent, f);
    }
    public Options withName(String fileBaseName, String extension)
    {
      NamerWrapper approvalNamer = new NamerWrapper(() -> fileBaseName, getNamer());
      FileOptions f = new FileOptions(approvalNamer, extension);
      return new Options(parent, f);
    }
  }
}
