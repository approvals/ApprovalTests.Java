package org.approvaltests.reporters;

import org.approvaltests.reporters.linux.KDiff3LinuxReporter;
import org.approvaltests.reporters.macosx.KDiff3MacReporter;
import org.approvaltests.reporters.windows.KDiff3WindowsReporter;

public class KDiff3Reporter extends FirstWorkingReporter
{
  public static final KDiff3Reporter INSTANCE = new KDiff3Reporter();
  public KDiff3Reporter()
  {
    super(KDiff3MacReporter.INSTANCE, KDiff3WindowsReporter.INSTANCE, KDiff3LinuxReporter.INSTANCE);
  }
}
