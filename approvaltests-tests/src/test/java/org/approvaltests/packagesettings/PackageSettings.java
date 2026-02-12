package org.approvaltests.packagesettings;

import org.approvaltests.reporters.macosx.ReportWithTkDiffMac;

// begin-snippet: use_reporter_package_settings
public class PackageSettings
{
  public static ReportWithTkDiffMac UseReporter         = ReportWithTkDiffMac.INSTANCE;
  public static CountingReporter    FrontloadedReporter = new CountingReporter();
}
// end-snippet
