package org.approvaltests.reporters.macosx;

import java.text.MessageFormat;

import org.approvaltests.reporters.GenericDiffReporter;

public class TkDiffReporter extends GenericDiffReporter
{
  private static final String        DIFF_PROGRAM = "/Applications/TkDiff.app/Contents/MacOS/tkdiff";
  static final String                MESSAGE      = MessageFormat.format("Unable to find TkDiffMerge at {0}",
                                                      DIFF_PROGRAM);
  public static final TkDiffReporter INSTANCE     = new TkDiffReporter();
  public TkDiffReporter()
  {
    super(DIFF_PROGRAM, GenericDiffReporter.STANDARD_ARGUMENTS, MESSAGE, GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  }
}
