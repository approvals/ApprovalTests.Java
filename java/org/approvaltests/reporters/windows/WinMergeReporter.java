package org.approvaltests.reporters.windows;

import java.text.MessageFormat;

import org.approvaltests.reporters.GenericDiffReporter;

public class WinMergeReporter extends GenericDiffReporter
{
  public static final WinMergeReporter INSTANCE     = new WinMergeReporter();                       ;
  static final String                  DIFF_PROGRAM = "C:\\Program Files\\WinMerge\\WinMergeU.exe";
  static final String                  MESSAGE      = MessageFormat.format("Unable to find WinMerge at {0}"
                                                        + "\nYou can install it at http://winmerge.org/",
                                                        DIFF_PROGRAM);
  public WinMergeReporter()
  {
    super(DIFF_PROGRAM, MESSAGE);
  }
}
