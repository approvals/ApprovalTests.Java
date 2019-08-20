package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;

import com.spun.util.io.FileUtils;
import com.spun.util.tests.TestUtils;

public class FileLauncherReporter implements ApprovalFailureReporter
{
  @Override
  public void report(String received, String approved)
  {
    if (FileUtils.isNonEmptyFile(approved))
    {
      TestUtils.displayFile(approved);
    }
    TestUtils.displayFile(received);
  }
}
