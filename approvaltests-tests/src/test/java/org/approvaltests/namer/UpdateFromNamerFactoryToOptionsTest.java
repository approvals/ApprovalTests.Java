package org.approvaltests.namer;

import com.spun.util.SystemUtils;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class UpdateFromNamerFactoryToOptionsTest
{
  @Test
  void withParameters()
  {
    String before;
    // begin-snippet: namer_factory_with_parameters_before
    try (NamedEnvironment en = NamerFactory.withParameters("title", "chapter"))
    {
      before = getApprovedFileName(new Options());
    }
    // end-snippet
    // begin-snippet: namer_factory_with_parameters_after
    Options options = Approvals.NAMES.withParameters("title", "chapter");
    // end-snippet
    assertEquals(before, getApprovedFileName(options));
  }

  @Test
  void asOsSpecificTest()
  {
    String before;
    // begin-snippet: namer_factory_os_specific_before
    try (NamedEnvironment en = NamerFactory.asOsSpecificTest())
    {
      before = getApprovedFileName(new Options());
    }
    // end-snippet
    // begin-snippet: namer_factory_os_specific_after
    Options options = Approvals.NAMES.asOsSpecificTest();
    // end-snippet
    assertEquals(before, getApprovedFileName(options));
  }

  @Test
  void asMachineNameSpecificTest()
  {
    String before;
    try (NamedEnvironment en = NamerFactory.asMachineNameSpecificTest())
    {
      before = getApprovedFileName(new Options());
    }
    // begin-snippet: namer_factory_machine_specific_after
    Options options = Approvals.NAMES.asMachineNameSpecificTest();
    // end-snippet
    assertEquals(before, getApprovedFileName(options));
  }

  @Test
  void asMachineSpecificTest()
  {
    String before;
    try (NamedEnvironment en = NamerFactory.asMachineSpecificTest("jdk17"))
    {
      before = getApprovedFileName(new Options());
    }
    // begin-snippet: namer_factory_custom_label_after
    Options options = new Options().forFile().withAdditionalInformation("jdk17");
    // end-snippet
    assertEquals(before, getApprovedFileName(options));
  }

  @Test
  void runOnlyOnSpecificMachines()
  {
    // begin-snippet: run_only_on_specific_machines
    assumeTrue(SystemUtils.getComputerName().equals("RSI-FQ61HDHJ0K"));
    // the rest of your test...
    // end-snippet
  }

  private static String getApprovedFileName(Options options)
  {
    return options.forFile().getNamer().getApprovedFile(".txt").getName();
  }
}
