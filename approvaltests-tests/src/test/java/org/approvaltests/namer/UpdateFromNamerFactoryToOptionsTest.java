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
    // begin-snippet: namer_factory_with_parameters_after
    Options options = Approvals.NAMES.withParameters("title", "chapter");
    // end-snippet
    assertEquals(
        "UpdateFromNamerFactoryToOptionsTest.withParameters.title.chapter.approved.txt",
        getApprovedFileName(options));
  }

  @Test
  void asOsSpecificTest()
  {
    // begin-snippet: namer_factory_os_specific_after
    Options options = Approvals.NAMES.asOsSpecificTest();
    // end-snippet
    assertEquals(
        "UpdateFromNamerFactoryToOptionsTest.asOsSpecificTest." + new OsEnvironmentLabeller().call()
            + ".approved.txt",
        getApprovedFileName(options));
  }

  @Test
  void asMachineNameSpecificTest()
  {
    // begin-snippet: namer_factory_machine_specific_after
    Options options = Approvals.NAMES.asMachineNameSpecificTest();
    // end-snippet
    assertEquals(
        "UpdateFromNamerFactoryToOptionsTest.asMachineNameSpecificTest." + SystemUtils.getComputerName()
            + ".approved.txt",
        getApprovedFileName(options));
  }

  @Test
  void asMachineSpecificTest()
  {
    // begin-snippet: namer_factory_custom_label_after
    Options options = new Options().forFile().withAdditionalInformation("jdk17");
    // end-snippet
    assertEquals(
        "UpdateFromNamerFactoryToOptionsTest.asMachineSpecificTest.jdk17.approved.txt",
        getApprovedFileName(options));
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
