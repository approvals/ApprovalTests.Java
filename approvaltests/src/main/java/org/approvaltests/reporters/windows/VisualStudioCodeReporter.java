package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class VisualStudioCodeReporter extends GenericDiffReporter
{
  public static final VisualStudioCodeReporter INSTANCE = new VisualStudioCodeReporter();
  public VisualStudioCodeReporter()
  {
    super(Windows.VISUAL_STUDIO_CODE);
  }
}
