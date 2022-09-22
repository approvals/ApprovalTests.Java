package org.approvaltests.utils.logger;

import com.spun.util.logger.SimpleLogger;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;

public class VerifySimpleLogger implements AutoCloseable
{
  private final StringBuffer log;
  private Options            options;
  public VerifySimpleLogger(Options options)
  {
    this.options = options;
    log = SimpleLogger.logToString();
  }
  @Override
  public void close()
  {
    Approvals.verify(log, options);
  }
}
