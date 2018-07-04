package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Windows;

public class VisualStudioCodeReporter extends GenericDiffReporter
{
  public static final VisualStudioCodeReporter INSTANCE = new VisualStudioCodeReporter();
  public VisualStudioCodeReporter()
  {
    super(Windows.VISUAL_STUDIO_CODE);
  }
}
