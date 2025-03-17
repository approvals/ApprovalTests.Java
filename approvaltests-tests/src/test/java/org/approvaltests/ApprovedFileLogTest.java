package org.approvaltests;

import com.spun.util.io.FileUtils;
import org.approvaltests.internal.logs.ApprovedFileLog;
import org.approvaltests.internal.logs.LoggingUtils;
import org.approvaltests.namer.ApprovalNamer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApprovedFileLogTest
{
  @Test
  void testLogging()
  {
    File file = ApprovedFileLog.get();
    ApprovalNamer approvalNamer = Approvals.createApprovalNamer();
    File approvedFile = approvalNamer.getApprovedFile(".txt");
    String prelog = FileUtils.readFile(file);
    // TODO
    Assert.assertFalse(prelog.contains(approvedFile.getAbsolutePath()));
    Approvals.verify("anything");
    String postlog = FileUtils.readFile(file);
    Assert.assertTrue(postlog.contains(approvedFile.getAbsolutePath()));
  }
  @Test
  void testTempDirectoryGetsGitIgnore()
  {
    String result = FileUtils.readFile(LoggingUtils.getTempDirectory() + "/.gitignore");
    assertEquals("*\n", result);
  }
}
