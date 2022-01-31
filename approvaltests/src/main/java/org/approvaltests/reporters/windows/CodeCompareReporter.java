package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class CodeCompareReporter extends GenericDiffReporter
{
  public static final CodeCompareReporter INSTANCE = new CodeCompareReporter();
  public CodeCompareReporter()
  {
    super(Windows.CODE_COMPARE);
  }
}
