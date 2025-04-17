package org.approvaltests;

import com.spun.util.io.FileUtils;
import org.approvaltests.internal.logs.ApprovedFileLog;
import org.approvaltests.internal.logs.LoggingUtils;
import org.approvaltests.namer.ApprovalNamer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApprovedFileLogTest
{
  @Test
  void testLogging()
  {
    File file = ApprovedFileLog.get();
    ApprovalNamer approvalNamer = Approvals.createApprovalNamer();
    File approvedFile = approvalNamer.getApprovedFile(".txt");
    String prelog = FileUtils.readFile(file);
    assertFalse(prelog.contains(FileUtils.getResolvedPath(approvedFile)));
    Approvals.verify("anything");
    String postlog = FileUtils.readFile(file);
    assertTrue(postlog.contains(FileUtils.getResolvedPath(approvedFile)));
  }
  @Test
  void testTempDirectoryGetsGitIgnore()
  {
    String result = FileUtils.readFile(LoggingUtils.getTempDirectory() + "/.gitignore");
    assertEquals("*\n", result);
  }
  @Test
  void testAbsoluteDirectory()
  {
    File file = new File("./temp.temp");
    String absolutePath = FileUtils.getResolvedPath(file);
    String currentDirectory = File.separator + "." + File.separator;
    assertFalse(absolutePath.contains(currentDirectory), absolutePath);
  }
}
