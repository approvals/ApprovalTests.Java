package org.approvaltests.approvers.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.approvaltests.approvers.FileApprover;

import com.spun.util.io.FileUtils;
import com.spun.util.tests.StackTraceReflectionResult;
import com.spun.util.tests.TestUtils;

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
    assertFalse("files are different", FileApprover.approveTextFile(f1, f2));
    f2 = createFile("1");
    assertTrue("files are the same", FileApprover.approveTextFile(f1, f2));
  }
  public void testApproveTextFileWithNonExsitantFile() throws Exception
  {
    File f1 = createFile("1");
    File f2 = new File("no exist");
    assertTrue(f1.exists());
    assertFalse(f2.exists());
    assertFalse(FileApprover.approveTextFile(f1, f2));
  }

  private File createFile(String string) throws IOException
  {
    File f = File.createTempFile("avc", "t");
    FileUtils.writeFile(f, string);
    return f;
  }
}
