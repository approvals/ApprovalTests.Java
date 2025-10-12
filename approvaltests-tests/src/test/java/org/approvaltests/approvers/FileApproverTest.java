package org.approvaltests.approvers;

import com.spun.util.ObjectUtils;
import com.spun.util.QuietAutoCloseable;
import com.spun.util.io.FileUtils;
import com.spun.util.tests.StackTraceReflectionResult;
import com.spun.util.tests.TestUtils;
import org.approvaltests.ApprovalSettings;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.core.VerifyResult;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.writers.ApprovalTextWriter;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function2;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    assertEquals(VerifyResult.FAILURE, FileApprover.approveTextFile(f2, f1), "files are different");
    f2 = createFile("1");
    assertEquals(VerifyResult.SUCCESS, FileApprover.approveTextFile(f2, f1), "files are the same");
  }

  @Test
  public void testApproveTextFileWithNonExsitantFile()
  {
    File f1 = createFile("1");
    File f2 = new File("no exist");
    assertTrue(f1.exists());
    assertFalse(f2.exists());
    assertEquals(VerifyResult.FAILURE, FileApprover.approveTextFile(f2, f1));
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
    Function2<File, File, VerifyResult> approveEverything = (r, a) -> VerifyResult.SUCCESS;
    Approvals.verify(new FileApprover(writer, namer, approveEverything));
    // end-snippet
  }

  @Test
  void testCustomError()
  {
    var expected = """
        java.lang.AssertionError: Custom message
        """;
    try (var old = ApprovalSettings.registerErrorGenerator((r, a) -> new AssertionError("Custom message")))
    {
      FileApprover fileApprover = new FileApprover(new File("a20.txt"), new File("b20.txt"), null, null);
      Approvals.verifyException(fileApprover::fail, new Options().inline(expected));
    }
  }
}
