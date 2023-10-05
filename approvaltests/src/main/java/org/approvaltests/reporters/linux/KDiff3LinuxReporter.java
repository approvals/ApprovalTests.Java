package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.DiffPrograms;
import org.approvaltests.reporters.GenericDiffReporter;

public class KDiff3LinuxReporter extends GenericDiffReporter
{
  public static final KDiff3LinuxReporter INSTANCE = new KDiff3LinuxReporter();
  public KDiff3LinuxReporter()
  {
    super(DiffPrograms.Linux.KDIFF3);
  }
}
