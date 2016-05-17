package org.approvaltests.reporters.macosx;

import java.text.MessageFormat;

import org.approvaltests.reporters.GenericDiffReporter;

public class DiffMergeReporter extends GenericDiffReporter
{
  private static final String           DIFF_PROGRAM = "/Applications/DiffMerge.app/Contents/MacOS/DiffMerge";
  static final String                   MESSAGE      = MessageFormat.format("Unable to find DiffMerge at {0}",
                                                         DIFF_PROGRAM);
  public static final DiffMergeReporter INSTANCE     = new DiffMergeReporter();
  public DiffMergeReporter()
  {
    super(DIFF_PROGRAM, "--nosplash %s %s", MESSAGE, GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  }
}
