package org.approvaltests.reporters;

import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import com.spun.util.tests.TestUtils;
import org.approvaltests.core.ApprovalFailureReporter;

public class FileLauncherReporter implements ApprovalFailureReporter
{
  @Override
  public boolean report(String received, String approved)
  {
    try
    {
      if (FileUtils.isNonEmptyFile(approved))
      {
        TestUtils.displayFile(approved);
      }
      TestUtils.displayFile(received);
      return true;
    }
    catch (Exception e)
    {
      SimpleLogger.warning("Error launching file.", e);
      return false;
    }
  }
}
