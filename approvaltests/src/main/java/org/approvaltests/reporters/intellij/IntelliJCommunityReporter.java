package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.DiffPrograms.All;
import org.approvaltests.reporters.GenericDiffReporter;

public class IntelliJCommunityReporter extends GenericDiffReporter
{
  public static final IntelliJCommunityReporter INSTANCE = new IntelliJCommunityReporter();
  public IntelliJCommunityReporter()
  {
    super(All.INTELLIJ_C);
  }
}
