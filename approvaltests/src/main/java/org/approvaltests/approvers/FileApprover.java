package org.approvaltests.approvers;

import java.io.File;
import java.io.IOException;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalReporterWithCleanUp;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.namer.ApprovalNamer;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;

public class FileApprover implements ApprovalApprover
{
  private File                 received;
  private File                 approved;
  private final ApprovalWriter writer;
  public FileApprover(ApprovalWriter writer, ApprovalNamer namer)
  {
    this.writer = writer;
    String base = String.format("%s%s", namer.getSourceFilePath(), namer.getApprovalName());
    received = new File(writer.getReceivedFilename(base));
    approved = new File(writer.getApprovalFilename(base));
  }
  public boolean approve() throws Exception
  {
    received = new File(writer.writeReceivedFile(received.getAbsolutePath()));
    return approveTextFile(received, approved);
  }
  public void cleanUpAfterSuccess(ApprovalFailureReporter reporter) throws Exception
  {
    received.delete();
    if (reporter instanceof ApprovalReporterWithCleanUp)
    {
      ((ApprovalReporterWithCleanUp) reporter).cleanUp(received.getAbsolutePath(), approved.getAbsolutePath());
    }
  }
  public void reportFailure(ApprovalFailureReporter reporter) throws Exception
  {
    reporter.report(received.getAbsolutePath(), approved.getAbsolutePath());
  }
  public void fail()
  {
    throw new Error(String.format("Failed Approval\n  Approved:%s\n  Received:%s", approved.getAbsolutePath(),
        received.getAbsolutePath()));
  }
  public static boolean approveTextFile(File expected, File actual) throws IOException
  {
    if (!expected.exists() || !actual.exists()) { return false; }
    String t1 = FileUtils.readFile(expected);
    String t2 = FileUtils.readFile(actual);
    return ObjectUtils.isEqual(t1, t2);
  }
}
