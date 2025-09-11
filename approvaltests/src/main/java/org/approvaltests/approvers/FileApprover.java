package org.approvaltests.approvers;

import com.spun.util.ObjectUtils;
import com.spun.util.QuietAutoCloseable;
import com.spun.util.io.FileUtils;
import org.approvaltests.internal.logs.ApprovedFileLog;
import org.approvaltests.internal.logs.FailedFileLog;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalReporterWithCleanUp;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.core.VerifyResult;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.reporters.ReporterWithApprovalPower;
import org.lambda.functions.Function2;

import java.io.File;

public class FileApprover implements ApprovalApprover
{
  public static final ApprovalTracker             tracker        = new ApprovalTracker();
  private static Function2<String, String, Error> errorGenerator = FileApprover::createError;
  private File                                    received;
  private File                                    approved;
  private final ApprovalWriter                    writer;
  private Function2<File, File, VerifyResult>     approver;
  public FileApprover(ApprovalWriter writer, ApprovalNamer namer)
  {
    this(writer, namer, FileApprover::approveTextFile);
  }
  public FileApprover(ApprovalWriter writer, ApprovalNamer namer, Function2<File, File, VerifyResult> approver)
  {
    this(namer.getReceivedFile(writer.getFileExtensionWithDot()),
        namer.getApprovedFile(writer.getFileExtensionWithDot()), writer, approver);
  }
  public FileApprover(File received, File approved, ApprovalWriter writer,
      Function2<File, File, VerifyResult> approver)
  {
    this.received = received;
    this.approved = approved;
    this.writer = writer;
    this.approver = approver;
  }
  public static QuietAutoCloseable registerErrorGenerator(Function2<String, String, Error> errorGenerator)
  {
    Function2<String, String, Error> old = FileApprover.errorGenerator;
    FileApprover.errorGenerator = errorGenerator;
    return () -> FileApprover.errorGenerator = old;
  }
  public VerifyResult approve()
  {
    tracker.assertUnique(FileUtils.getResolvedPath(approved));
    ApprovedFileLog.log(approved);
    FailedFileLog.touch();
    received = writer.writeReceivedFile(received);
    return approver.call(received, approved);
  }
  public void cleanUpAfterSuccess(ApprovalFailureReporter reporter)
  {
    received.delete();
    if (reporter instanceof ApprovalReporterWithCleanUp)
    {
      ((ApprovalReporterWithCleanUp) reporter).cleanUp(FileUtils.getResolvedPath(received),
          FileUtils.getResolvedPath(approved));
    }
  }
  public VerifyResult reportFailure(ApprovalFailureReporter reporter)
  {
    FailedFileLog.log(received, approved);
    reporter.report(FileUtils.getResolvedPath(received), FileUtils.getResolvedPath(approved));
    if (reporter instanceof ReporterWithApprovalPower)
    {
      ReporterWithApprovalPower reporterWithApprovalPower = (ReporterWithApprovalPower) reporter;
      return reporterWithApprovalPower.approveWhenReported();
    }
    return VerifyResult.FAILURE;
  }
  public void fail()
  {
    throw errorGenerator.call(FileUtils.getResolvedPath(received), FileUtils.getResolvedPath(approved));
  }
  private static Error createError(String received, String approved)
  {
    return new Error(String.format("Failed Approval\n  Approved:%s\n  Received:%s", approved, received));
  }
  public static VerifyResult approveTextFile(File received, File approved)
  {
    if (!approved.exists() || !received.exists())
    { return VerifyResult.FAILURE; }
    String t1 = FileUtils.readFile(approved);
    String t2 = FileUtils.readFile(received);
    return VerifyResult.from(ObjectUtils.isEqual(t1, t2));
  }
}
