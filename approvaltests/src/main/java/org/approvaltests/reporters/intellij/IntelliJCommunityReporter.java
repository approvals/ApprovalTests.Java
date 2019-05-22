package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.All;

public class IntelliJCommunityReporter extends GenericDiffReporter
{
  public static final IntelliJCommunityReporter INSTANCE = new IntelliJCommunityReporter();
  public IntelliJCommunityReporter()
  {
    super(All.INTELLIJ_C);
  }
}
