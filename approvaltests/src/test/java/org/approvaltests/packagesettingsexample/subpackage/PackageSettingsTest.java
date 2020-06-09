package org.approvaltests.packagesettingsexample.subpackage;

import org.approvaltests.Approvals;
import org.junit.Test;
import org.packagesettings.PackageLevelSettings;

public class PackageSettingsTest
{
  @Test
  public void testRetriveValueWithOverRide() throws Exception
  {
    Approvals.verify(PackageLevelSettings.get());
  }
}
