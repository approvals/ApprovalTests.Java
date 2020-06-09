package org.approvaltests.packagesettingsexample;

import java.util.Map;

import org.approvaltests.Approvals;
import org.junit.Test;
import org.packagesettings.PackageLevelSettings;
import org.packagesettings.Settings;

public class PackageSettingsTest
{
  @Test
  public void testRetriveValue() throws Exception
  {
    // begin-snippet: package_level_settings_get
    Map<String, Settings> settings = PackageLevelSettings.get();
    // end-snippet
    Approvals.verify(settings);
  }
}
