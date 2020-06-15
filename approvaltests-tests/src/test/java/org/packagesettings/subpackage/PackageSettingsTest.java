package org.packagesettings.subpackage;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.packagesettings.PackageLevelSettings;

public class PackageSettingsTest
{
  @Test
  public void testRetriveValueWithOverRide()
  {
    Approvals.verify(PackageLevelSettings.get());
  }
}
