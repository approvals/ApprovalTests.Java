package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.DiffPrograms.All;
import org.approvaltests.reporters.GenericDiffReporter;

public class IntelliJMacSiliconReporter extends GenericDiffReporter
{
  public static final IntelliJMacSiliconReporter INSTANCE = new IntelliJMacSiliconReporter();
  public IntelliJMacSiliconReporter()
  {
    super(All.INTELLIJ_MAC_SILICON);
  }
}
