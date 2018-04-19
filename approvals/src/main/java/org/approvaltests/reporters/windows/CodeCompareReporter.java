package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Windows;

public class CodeCompareReporter extends GenericDiffReporter
{
  public static final CodeCompareReporter INSTANCE = new CodeCompareReporter();
  public CodeCompareReporter()
  {
    super(Windows.CODE_COMPARE);
  }
}
