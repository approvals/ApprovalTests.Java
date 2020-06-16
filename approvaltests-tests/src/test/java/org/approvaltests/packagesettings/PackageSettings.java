package org.approvaltests.packagesettings;

import org.approvaltests.reporters.macosx.TkDiffReporter;

// begin-snippet: use_reporter_package_settings
public class PackageSettings
{
  public static TkDiffReporter   UseReporter         = TkDiffReporter.INSTANCE;
  public static CountingReporter FrontloadedReporter = new CountingReporter();
}
// end-snippet
