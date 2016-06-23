package org.approvaltests.reporters;

public class DefaultFrontLoadedReporter extends FirstWorkingReporter
{
  public static final DefaultFrontLoadedReporter INSTANCE = new DefaultFrontLoadedReporter();
  public DefaultFrontLoadedReporter()
  {
    super(PitReporter.INSTANCE);
  }
}
