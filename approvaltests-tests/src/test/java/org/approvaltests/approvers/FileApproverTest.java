package org.approvaltests.approvers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.writers.ApprovalTextWriter;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function2;
import org.lambda.functions.Function3;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.tests.StackTraceReflectionResult;
import com.spun.util.tests.TestUtils;

public class FileApproverTest
{
  @Test
  public void testGetCurrentFileForMethod()
  {
    StackTraceReflectionResult info = TestUtils.getCurrentFileForMethod(0);
    assertEquals("FileApproverTest", info.getClassName());
    assertEquals("testGetCurrentFileForMethod", info.getMethodName());
  }
  @Test
  public void testApproveTextFile()
  {
    File f1 = createFile("1");
    File f2 = createFile("2");
    assertFalse(FileApprover.approveTextFile(f2, f1, false), "files are different");
    f2 = createFile("1");
    assertTrue(FileApprover.approveTextFile(f2, f1, false), "files are the same");
  }
  @Test
  public void testApproveTextFileWithNonExsitantFile()
  {
    File f1 = createFile("1");
    File f2 = new File("no exist");
    assertTrue(f1.exists());
    assertFalse(f2.exists());
    assertFalse(FileApprover.approveTextFile(f2, f1, false));
  }
  @Test
  public void testApproveTextFileWithNonExsitantFileCreated()
  {
    File received = createFile("1");
    File approved = new File("no-exist");
    assertTrue(received.exists());
    assertFalse(approved.exists());
    assertTrue(FileApprover.approveTextFile(received, approved, true));
    assertTrue(approved.exists());
    assertTrue(approved.delete());
  }
  private File createFile(String string)
  {
    try
    {
      File f = File.createTempFile("avc", "t");
      FileUtils.writeFile(f, string);
      return f;
    }
    catch (IOException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  @Test
  public void testCustomApprover()
  {
    // begin-snippet: custom_approver
    ApprovalTextWriter writer = new ApprovalTextWriter("Random: ", new Options());
    ApprovalNamer namer = Approvals.createApprovalNamer();
    Function3<File, File, Boolean, Boolean> approveEverything = (r, a, b) -> true;
    Approvals.verify(new FileApprover(writer, namer, approveEverything, new Options()));
    // end-snippet
  }
}
