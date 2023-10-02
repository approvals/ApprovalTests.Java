package org.approvaltests.reporters;

import com.spun.util.logger.SimpleLogger;
import org.approvaltests.core.ApprovalFailureReporter;

public class QuietReporter implements ApprovalFailureReporter
{
  public static final QuietReporter INSTANCE = new QuietReporter();
  @Override
  public boolean report(String received, String approved)
  {
    SimpleLogger.message(ClipboardReporter.getAcceptApprovalText(received, approved));
    return true;
  }
}
