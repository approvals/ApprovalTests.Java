package org.approvaltests.core;

import java.util.Optional;

import org.approvaltests.Approvals;
import org.approvaltests.ReporterFactory;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.NamerWrapper;
import org.approvaltests.scrubbers.NoOpScrubber;

public class Options
{
  private Scrubber                          scrubber    = NoOpScrubber.INSTANCE;
  private Optional<ApprovalFailureReporter> reporter    = Optional.empty();
  private FileOptions                       fileOptions = new FileOptions();
  public Options()
  {
  }
  public Options(Scrubber scrubber)
  {
    this.scrubber = scrubber;
  }
  public Options(ApprovalFailureReporter reporter)
  {
    this.reporter = Optional.ofNullable(reporter);
  }
  private Options(Scrubber scrubber, Optional<ApprovalFailureReporter> reporter, FileOptions fileOptions)
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
    return this.reporter.orElse(ReporterFactory.get());
  }
  public Options withReporter(ApprovalFailureReporter reporter)
  {
    return new Options(this.scrubber, Optional.ofNullable(reporter), fileOptions);
  }
  public Options withScrubber(Scrubber scrubber)
  {
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
    private boolean        createApprovedFileIfNoneExisting = false;
    protected NamerWrapper approvalNamer = null;
    protected Options      parent;
    public FileOptions()
    {
    }
    public FileOptions(String fileExtension)
    {
      this.fileExtension = fileExtension;
    }
    public FileOptions(NamerWrapper approvalNamer, String fileExtension, boolean createApprovedFileIfNoneExisting)
    {
      this.approvalNamer = approvalNamer;
      this.fileExtension = fileExtension;
      this.createApprovedFileIfNoneExisting = createApprovedFileIfNoneExisting;
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
      FileOptions f = new FileOptions(this.approvalNamer, fileExtensionWithDot, createApprovedFileIfNoneExisting);
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
    public boolean shouldCreateApprovedFileIfNoneExisting() {
		return createApprovedFileIfNoneExisting;
	}
    public Options withBaseName(String fileBaseName)
    {
      NamerWrapper approvalNamer = new NamerWrapper(() -> fileBaseName, getNamer());
      FileOptions f = new FileOptions(approvalNamer, this.fileExtension, createApprovedFileIfNoneExisting);
      return new Options(parent, f);
    }

    public Options withName(String fileBaseName, String extension) {
      NamerWrapper approvalNamer = new NamerWrapper(() -> fileBaseName, getNamer());
      FileOptions f = new FileOptions(approvalNamer, extension, createApprovedFileIfNoneExisting);
      return new Options(parent, f);
    }

    public Options withCreateApprovedFileIfNoneExisting(boolean createApprovedFileIfNoneExisting) {
      FileOptions f = new FileOptions(approvalNamer, fileExtension, createApprovedFileIfNoneExisting);
      return new Options(parent, f);
    }
  }
}
