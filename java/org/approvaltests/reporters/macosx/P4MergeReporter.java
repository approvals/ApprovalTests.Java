package org.approvaltests.reporters.macosx;

import java.text.MessageFormat;
import java.util.List;

import org.approvaltests.reporters.GenericDiffReporter;

import com.spun.util.ArrayUtils;

public class P4MergeReporter extends GenericDiffReporter
{
  private static final String         DIFF_PROGRAM = "/Applications/p4merge.app/Contents/MacOS/p4merge";
  public static final String          MESSAGE      = MessageFormat
                                                       .format(
                                                           "Unable to find P4Merge at {0}"
                                                               + "\nYou can install P4Merge "
                                                               + "at http://www.perforce.com/downloads/Perforce/20-User#10",
                                                           DIFF_PROGRAM);
  private static List<String>         fileTypes    = ArrayUtils.combine(GenericDiffReporter.IMAGE_FILE_EXTENSIONS,
                                                       GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  public static final P4MergeReporter INSTANCE     = new P4MergeReporter();
  public P4MergeReporter()
  {
    super(DIFF_PROGRAM, GenericDiffReporter.STANDARD_ARGUMENTS, P4MergeReporter.MESSAGE, fileTypes);
  }
}
