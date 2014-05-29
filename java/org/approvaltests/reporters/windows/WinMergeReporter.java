package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;

public class WinMergeReporter extends GenericDiffReporter
{
  public static String[] POSSIBLE_LOCATIONS = new String[]{ "C:\\Program Files\\WinMerge\\WinMergeU.exe",
                                                            "C:\\Program Files (x86)\\WinMerge\\WinMergeU.exe",
                                                            "C:\\Programs\\WinMerge\\WinMergeU.exe"};
  public static final WinMergeReporter INSTANCE     = new WinMergeReporter();                       ;
  
  public WinMergeReporter()
  {
    super(POSSIBLE_LOCATIONS, GenericDiffReporter.WINDOWS_ARGUMENT_FORMAT);
  }
}
