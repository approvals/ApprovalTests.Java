package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.FirstWorkingReporter;

public class IntelliJReporter extends FirstWorkingReporter
{
  public static final IntelliJReporter INSTANCE = new IntelliJReporter();
  public IntelliJReporter()
  {
    super(IntelliJUltimateReporter.INSTANCE, IntelliJCommunityReporter.INSTANCE);
  }
}
