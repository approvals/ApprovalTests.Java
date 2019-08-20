package org.approvaltests.reporters;

import java.io.File;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;

import junit.framework.TestCase;

public class JunitReporter implements EnvironmentAwareReporter
{
  public static final JunitReporter INSTANCE = new JunitReporter();
  @Override
  public void report(String received, String approved)
  {
    String aText = new File(approved).exists() ? FileUtils.readFile(approved) : "";
    String rText = FileUtils.readFile(received);
    String approveCommand = "To approve run : " + ClipboardReporter.getAcceptApprovalText(received, approved);
    System.out.println(approveCommand);
    TestCase.assertEquals(aText, rText);
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    try
    {
      ObjectUtils.loadClass("junit.framework.TestCase");
    }
    catch (Throwable t)
    {
      return false;
    }
    return GenericDiffReporter.isFileExtensionValid(forFile, GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  }
}