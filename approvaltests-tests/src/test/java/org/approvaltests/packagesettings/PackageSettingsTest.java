package org.approvaltests.packagesettings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

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
