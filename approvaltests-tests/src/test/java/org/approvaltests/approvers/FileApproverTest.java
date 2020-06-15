package org.approvaltests.approvers;

import java.io.File;
import java.io.IOException;

import org.approvaltests.Approvals;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.writers.ApprovalTextWriter;
import org.junit.Test;
import org.lambda.functions.Function2;

import com.spun.util.io.FileUtils;
import com.spun.util.tests.StackTraceReflectionResult;
import com.spun.util.tests.TestUtils;

import junit.framework.TestCase;

public class FileApproverTest extends TestCase
{
  public void testGetCurrentFileForMethod() throws Exception
  {
    StackTraceReflectionResult info = TestUtils.getCurrentFileForMethod(0);
    assertEquals("FileApproverTest", info.getClassName());
    assertEquals("testGetCurrentFileForMethod", info.getMethodName());
  }
  public void testApproveTextFile() throws Exception
  {
    File f1 = createFile("1");
    File f2 = createFile("2");
    assertFalse("files are different", FileApprover.approveTextFile(f2, f1));
    f2 = createFile("1");
    assertTrue("files are the same", FileApprover.approveTextFile(f2, f1));
  }
  public void testApproveTextFileWithNonExsitantFile() throws Exception
  {
    File f1 = createFile("1");
    File f2 = new File("no exist");
    assertTrue(f1.exists());
    assertFalse(f2.exists());
    assertFalse(FileApprover.approveTextFile(f2, f1));
  }
  private File createFile(String string) throws IOException
  {
    File f = File.createTempFile("avc", "t");
    FileUtils.writeFile(f, string);
    return f;
  }
  @Test
  public void testCustomApprover()
  {
    // begin-snippet: custom_approver
    ApprovalTextWriter writer = new ApprovalTextWriter("Random: ", "txt");
    ApprovalNamer namer = Approvals.createApprovalNamer();
    Function2<File, File, Boolean> approveEverything = (r, a) -> true;
    Approvals.verify(new FileApprover(writer, namer, approveEverything));
    // end-snippet
  }
}
