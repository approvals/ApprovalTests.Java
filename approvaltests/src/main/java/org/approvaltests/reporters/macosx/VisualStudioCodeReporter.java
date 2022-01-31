package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class VisualStudioCodeReporter extends GenericDiffReporter
{
  public static final VisualStudioCodeReporter INSTANCE = new VisualStudioCodeReporter();
  public VisualStudioCodeReporter()
  {
    super(Mac.VISUAL_STUDIO_CODE);
  }
}
