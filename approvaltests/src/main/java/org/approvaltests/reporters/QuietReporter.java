package org.approvaltests.reporters;

import com.spun.util.logger.SimpleLogger;

public class QuietReporter implements EnvironmentAwareReporter
{
  public static final QuietReporter INSTANCE = new QuietReporter();
  @Override
  public void report(String received, String approved)
  {
    SimpleLogger.message(ClipboardReporter.getAcceptApprovalText(received, approved));
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    return true;
  }
}
