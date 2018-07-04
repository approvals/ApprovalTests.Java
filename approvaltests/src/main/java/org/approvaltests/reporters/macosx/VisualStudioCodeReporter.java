package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Mac;

public class VisualStudioCodeReporter extends GenericDiffReporter
{
  public static final VisualStudioCodeReporter INSTANCE = new VisualStudioCodeReporter();
  public VisualStudioCodeReporter()
  {
    super(Mac.VISUAL_STUDIO_CODE);
  }
}