package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.DiffPrograms;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.GenericDiffReporter;

public class IntelliJReporter extends FirstWorkingReporter
{
  public static final IntelliJReporter INSTANCE = new IntelliJReporter();
  public IntelliJReporter()
  {
    super(IntelliJMacReporter.INSTANCE, IntelliJMacSiliconReporter.INSTANCE, IntelliJUltimateReporter.INSTANCE,
        IntelliJCommunityReporter.INSTANCE);
  }
  private static class IntelliJMacReporter extends GenericDiffReporter
  {
    public static final IntelliJMacReporter INSTANCE = new IntelliJMacReporter();
    public IntelliJMacReporter()
    {
      super(DiffPrograms.All.INTELLIJ);
    }
  }
}
