package org.approvaltests.machine_specific_tests;

import com.spun.util.SystemUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class MachineSpecificTest
{
  public static boolean DISPLAYED = false;
  @BeforeEach
  public void beforeMethod(TestInfo testInfo)
  {
    if (!MachineSpecific.isMachineConfiguredForTesting())
    {
      displayMessage(testInfo);
      assumeTrue(false);
    }
  }

  private void displayMessage(TestInfo testInfo)
  {
    if (!DISPLAYED)
    {
      DISPLAYED = true;
      String testName = testInfo.getTestClass().get().getName();
      String message = String.format("" //
          + "This machine isn't configured to run MachineSpecificTest.\n" //
          + "\n" //
          + "To run these either\n" //
          + "  1) Set machine_specific_tests.MachineSpecific.FORCE_RUN=true\n" //
          + "  2) Add \"%s\" to machine_specific_tests.MachineSpecific.MACHINES\n" //
          + "\n" //
          + "Triggered by %s", SystemUtils.getComputerName(), testName);
      SimpleLogger.warning(message);
    }
  }
}
