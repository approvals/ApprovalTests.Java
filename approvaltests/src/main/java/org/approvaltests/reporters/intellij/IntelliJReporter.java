package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.FirstWorkingReporter;

public class IntelliJReporter extends FirstWorkingReporter
{
  public static final IntelliJReporter INSTANCE = new IntelliJReporter();
  public IntelliJReporter()
  {
    super(IntelliJMacSiliconReporter.INSTANCE, IntelliJUltimateReporter.INSTANCE,
        IntelliJCommunityReporter.INSTANCE);
  }
}
