package org.approvaltests.utils.logger;

import org.approvaltests.core.Options;

import javax.swing.text.html.Option;

public class SimpleLoggerApprovals
{
  public static VerifySimpleLogger verify()
  {
    return verify(new Options());
  }

  public static VerifySimpleLogger verify(Options options)
  {
    return new VerifySimpleLogger(options);
  }
}
