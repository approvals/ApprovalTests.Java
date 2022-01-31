package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.DiffPrograms.All;
import org.approvaltests.reporters.GenericDiffReporter;

public class IntelliJUltimateReporter extends GenericDiffReporter
{
  public static final IntelliJUltimateReporter INSTANCE = new IntelliJUltimateReporter();
  public IntelliJUltimateReporter()
  {
    super(All.INTELLIJ_U);
  }
}
