package org.approvaltests.packagesettings;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
