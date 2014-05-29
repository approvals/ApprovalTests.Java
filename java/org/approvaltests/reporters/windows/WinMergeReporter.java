package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;

public class WinMergeReporter extends GenericDiffReporter
{
  public static final WinMergeReporter INSTANCE     = new WinMergeReporter();                       ;
  private static final String[] POSSIBLE_LOCATIONS = { "C:\\Program Files\\WinMerge\\WinMergeU.exe",
                                                       "C:\\Program Files (x86)\\WinMerge\\WinMergeU.exe",
                                                       "C:\\Programs\\WinMerge\\WinMergeU.exe"} ;
  public WinMergeReporter()
  {
    super(POSSIBLE_LOCATIONS, GenericDiffReporter.WINDOWS_ARGUMENT_FORMAT);
  }
}
