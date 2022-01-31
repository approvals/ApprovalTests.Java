package org.approvaltests.reporters;

import com.spun.util.io.FileUtils;
import com.spun.util.tests.TestUtils;
import org.approvaltests.core.ApprovalFailureReporter;

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
