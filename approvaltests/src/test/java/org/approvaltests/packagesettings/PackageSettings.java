package org.approvaltests.packagesettings;

import org.approvaltests.reporters.macosx.TkDiffReporter;

// startcode use_reporter_package_settings
public class PackageSettings
{
  public static TkDiffReporter   UseReporter         = TkDiffReporter.INSTANCE;
  public static CountingReporter FrontloadedReporter = new CountingReporter();
}
// endcode