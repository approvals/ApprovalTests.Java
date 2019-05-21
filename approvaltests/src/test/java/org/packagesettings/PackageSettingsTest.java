package org.packagesettings;

import java.util.Map;

import org.approvaltests.Approvals;
import org.junit.Test;

public class PackageSettingsTest
{
  @Test
  public void testRetriveValue() throws Exception
  {
    // startcode package_level_settings_get
    Map<String, Settings> settings = PackageLevelSettings.get();
    // endcode
    Approvals.verify(settings);
  }
}
