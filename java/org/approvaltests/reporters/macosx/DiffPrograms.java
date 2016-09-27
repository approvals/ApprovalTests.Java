package org.approvaltests.reporters.macosx;

import java.util.List;

import org.approvaltests.reporters.DiffInfo;
import org.approvaltests.reporters.GenericDiffReporter;

public class DiffPrograms
{
  private static final List<String> TEXT = GenericDiffReporter.TEXT_FILE_EXTENSIONS;
  public static class Mac
  {
    public static DiffInfo DIFF_MERGE = new DiffInfo("/Applications/DiffMerge.app/Contents/MacOS/DiffMerge",
        "%s %s --nosplash", TEXT);
  }
}