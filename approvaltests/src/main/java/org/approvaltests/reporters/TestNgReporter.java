package org.approvaltests.reporters;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.testng.Assert;

import java.io.File;

public class TestNgReporter implements ApprovalFailureReporter
{
  public static final TestNgReporter INSTANCE = new TestNgReporter();
  @Override
  public boolean report(String received, String approved)
  {
    if (!isWorkingInThisEnvironment(received))
    {
      QuietReporter.INSTANCE.report(received, approved);
      return false;
    }
    String aText = new File(approved).exists() ? FileUtils.readFile(approved) : "";
    String rText = FileUtils.readFile(received);
    Assert.assertEquals(aText, rText);
    return true;
  }

  private boolean isWorkingInThisEnvironment(String forFile)
  {
    try
    {
      ObjectUtils.loadClass("org.testng.annotations.Test");
    }
    catch (Throwable t)
    {
      return false;
    }
    return GenericDiffReporter.isFileExtensionValid(forFile, GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  }
}
