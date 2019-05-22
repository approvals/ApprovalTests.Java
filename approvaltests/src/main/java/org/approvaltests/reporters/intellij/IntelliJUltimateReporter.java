package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.DiffPrograms.All;

public class IntelliJUltimateReporter extends GenericDiffReporter
{
  public static final IntelliJUltimateReporter INSTANCE = new IntelliJUltimateReporter();
  public IntelliJUltimateReporter()
  {
    super(All.INTELLIJ_U);
  }
}
