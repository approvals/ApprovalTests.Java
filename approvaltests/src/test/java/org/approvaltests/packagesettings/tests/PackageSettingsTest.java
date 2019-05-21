package org.approvaltests.packagesettings.tests;

import static org.junit.Assert.assertEquals;

import org.approvaltests.Approvals;
import org.approvaltests.packagesettings.PackageSettings;
import org.junit.Test;

public class PackageSettingsTest
{
  @Test
  public void testFrontLoadedReporter()
  {
    PackageSettings.FrontloadedReporter.count = 10;
    failApproval();
    assertEquals(11, PackageSettings.FrontloadedReporter.count);
  }
  private void failApproval()
  {
    try
    {
      Approvals.verify("foo");
    }
    catch (Throwable e)
    {
      // ignore
    }
  }
}
