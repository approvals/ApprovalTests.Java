package org.approvaltests.inline;

import org.approvaltests.core.Options;
import org.approvaltests.reporters.AutoApproveReporter;

public interface InlineOptions
{
  Options apply(Options options);
  public static InlineOptions showCode(boolean doShowCode)
  {
    if (doShowCode)
    {
      return options -> options.withReporter(new InlineJavaReporter(options.getReporter(), false));
    }
    else
    {
      return options -> options;
    }
  }
  public static InlineOptions automatic()
  {
    return options -> options.withReporter(new InlineJavaReporter(new AutoApproveReporter(), false));
  }
  public static InlineOptions semiAutomatic()
  {
    return options -> options.withReporter(new InlineJavaReporter(new AutoApproveReporter(), true));
  }
}
