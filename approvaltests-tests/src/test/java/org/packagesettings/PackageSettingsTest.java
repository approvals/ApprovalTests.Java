package org.packagesettings;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class PackageSettingsTest
{
  @Test
  public void testRetrieveValue()
  {
    // begin-snippet: package_level_settings_get
    Map<String, Settings> settings = PackageLevelSettings.get();
    // end-snippet
    Approvals.verify(settings);
  }
}
