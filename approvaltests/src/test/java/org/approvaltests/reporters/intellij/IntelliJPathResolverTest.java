package org.approvaltests.reporters.intellij;

import org.approvaltests.Approvals;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.junit.jupiter.api.Test;

//@UseReporter(IntelliJReporter.class)
public class IntelliJPathResolverTest
{
  @Test
  void testDetectionOfIntellijOnDevMachines()
  {
    // begin-snippet: runOnlyOnSpecificMachines
    try (NamedEnvironment namedEnvironment = NamerFactory.asMachineNameSpecificTest())
    {
      if (!namedEnvironment.isCurrentEnvironmentValidFor(".RSI-FQ61HDHJ0K"))
      { return; }
      // the rest of your test...
      // end-snippet
      //        Approvals.verify("hey");
    }
  }
}
