package org.approvaltests.reporters;

import java.io.File;

import org.testng.Assert;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;

public class TestNgReporter implements EnvironmentAwareReporter
{
  public static final TestNgReporter INSTANCE = new TestNgReporter();
  @Override
  public void report(String received, String approved)
  {
    if (!isWorkingInThisEnvironment(received))
    {
      QuietReporter.INSTANCE.report(received, approved);
      return;
    }
    String aText = new File(approved).exists() ? FileUtils.readFile(approved) : "";
    String rText = FileUtils.readFile(received);
    Assert.assertEquals(aText, rText);
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
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