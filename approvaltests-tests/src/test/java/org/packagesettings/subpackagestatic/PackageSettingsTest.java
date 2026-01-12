package org.packagesettings.subpackagestatic;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.packagesettings.PackageLevelSettings;

public class PackageSettingsTest
{
  // https://docs.openrewrite.org/recipes/staticanalysis/hideutilityclassconstructor says:
  //     Ensures utility classes (classes containing only static methods or fields in their API) do not have a public constructor.
  @Test
  public void testRetrieveValueWithPrivateConstructor()
  {
    Approvals.verify(PackageLevelSettings.get());
  }
}
