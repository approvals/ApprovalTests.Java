package org.packagesettings.subpackage;

import static org.junit.Assert.fail;

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
  /*
  @Test
  public void testName() throws Exception
  {
    fail("boo");
  }
   */
}
