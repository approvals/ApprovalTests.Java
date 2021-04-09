package org.approvaltests.approvers;

import java.io.File;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalReporterWithCleanUp;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.core.VerifyResult;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.reporters.ReporterWithApprovalPower;
import org.lambda.functions.Function2;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;

public class FileApprover implements ApprovalApprover
{
  private File                                received;
  private File                                approved;
  private final ApprovalWriter                writer;
  private Function2<File, File, VerifyResult> approver;
  public FileApprover(ApprovalWriter writer, ApprovalNamer namer)
  {
    this(writer, namer, FileApprover::approveTextFile);
  }
  public FileApprover(ApprovalWriter writer, ApprovalNamer namer, Function2<File, File, VerifyResult> approver)
  {
    this.writer = writer;
    received = namer.getReceivedFile(writer.getFileExtensionWithDot());
    approved = namer.getApprovedFile(writer.getFileExtensionWithDot());
    this.approver = approver;
  }
  public VerifyResult approve()
  {
    received = writer.writeReceivedFile(received);
    return approver.call(received, approved);
  }
  public void cleanUpAfterSuccess(ApprovalFailureReporter reporter)
  {
    received.delete();
    if (reporter instanceof ApprovalReporterWithCleanUp)
    {
      ((ApprovalReporterWithCleanUp) reporter).cleanUp(received.getAbsolutePath(), approved.getAbsolutePath());
    }
  }
  public VerifyResult reportFailure(ApprovalFailureReporter reporter)
  {
    reporter.report(received.getAbsolutePath(), approved.getAbsolutePath());
    if (reporter instanceof ReporterWithApprovalPower)
    {
      ReporterWithApprovalPower reporterWithApprovalPower = (ReporterWithApprovalPower) reporter;
      return reporterWithApprovalPower.approveWhenReported();
    }
    return VerifyResult.FAILURE;
  }
  public void fail()
  {
    throw new Error(String.format("Failed Approval\n  Approved:%s\n  Received:%s", approved.getAbsolutePath(),
        received.getAbsolutePath()));
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
