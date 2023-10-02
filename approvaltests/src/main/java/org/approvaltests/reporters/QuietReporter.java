package org.approvaltests.reporters;

import com.spun.util.logger.SimpleLogger;

public class QuietReporter implements EnvironmentAwareReporter
{
  public static final QuietReporter INSTANCE = new QuietReporter();
  @Override
  public boolean report(String received, String approved)
  {
    SimpleLogger.message(ClipboardReporter.getAcceptApprovalText(received, approved));
    return true;
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    return true;
  }
}
