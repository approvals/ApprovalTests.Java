package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.intellij.IntelliJCommunityReporter;
import org.approvaltests.reporters.intellij.IntelliJUltimateReporter;

public class IntelliJReporter extends FirstWorkingReporter
{
  public static final IntelliJReporter INSTANCE = new IntelliJReporter();
  public IntelliJReporter()
  {
    super(IntelliJUltimateReporter.INSTANCE, IntelliJCommunityReporter.INSTANCE);
  }
}
