package org.approvaltests.approvers;

import java.io.File;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalReporterWithCleanUp;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.namer.ApprovalNamer;
import org.lambda.functions.Function2;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;

public class FileApprover implements ApprovalApprover
{
  private File                           received;
  private File                           approved;
  private final ApprovalWriter           writer;
  private Function2<File, File, Boolean> approver;
  public FileApprover(ApprovalWriter writer, ApprovalNamer namer)
  {
    this(writer, namer, FileApprover::approveTextFile);
  }
  public FileApprover(ApprovalWriter writer, ApprovalNamer namer, Function2<File, File, Boolean> approver)
  {
    this.writer = writer;
    String base = String.format("%s%s", namer.getSourceFilePath(), namer.getApprovalName());
    received = new File(writer.getReceivedFilename(base));
    approved = new File(writer.getApprovalFilename(base));
    this.approver = approver;
  }
  public boolean approve()
  {
    received = new File(writer.writeReceivedFile(received.getAbsolutePath()));
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
  public void reportFailure(ApprovalFailureReporter reporter)
  {
    reporter.report(received.getAbsolutePath(), approved.getAbsolutePath());
  }
  public void fail()
  {
    throw new Error(String.format("Failed Approval\n  Approved:%s\n  Received:%s", approved.getAbsolutePath(),
        received.getAbsolutePath()));
  }
  public static Boolean approveTextFile(File received, File approved)
  {
    if (!approved.exists() || !received.exists()) { return false; }
    String t1 = FileUtils.readFile(approved);
    String t2 = FileUtils.readFile(received);
    return ObjectUtils.isEqual(t1, t2);
  }
}
