package org.approvaltests.reporters;

public class DefaultFrontLoadedReporter extends FirstWorkingReporter
{
  public static final DefaultFrontLoadedReporter INSTANCE = new DefaultFrontLoadedReporter();
  public DefaultFrontLoadedReporter()
  {
    super(
            // begin-snippet: default_front_loaded_reporter
            PitReporter.INSTANCE
            // end-snippet
    );
  }
}
