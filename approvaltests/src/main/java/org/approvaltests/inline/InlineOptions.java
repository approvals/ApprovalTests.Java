package org.approvaltests.inline;

import org.approvaltests.core.Options;

public interface InlineOptions
{
  Options apply(Options options);
  public static InlineOptions showCode(boolean doShowCode)
  {
    if (doShowCode)
    {
      return new ShowCodeInlineOptions();
    }
    else
    {
      return new DoNotShowCodeInlineOptions();
    }
  }
  public static class ShowCodeInlineOptions implements InlineOptions
  {
    public Options apply(Options options)
    {
      return options.withReporter(new InlineJavaReporter(options.getReporter()));
    }
  }
  public static class DoNotShowCodeInlineOptions implements InlineOptions
  {
    public Options apply(Options options)
    {
      return options;
    }
  }
}
