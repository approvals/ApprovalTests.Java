package org.approvaltests.machine_specific_tests;

import com.spun.util.SystemUtils;
import com.spun.util.logger.SimpleLogger;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class MachineSpecificTest
{
  private static boolean DISPLAYED = false;
  @BeforeEach
  public void beforeMethod()
  {
    if (!MachineSpecific.isMachineConfiguredForTesting())
    {
      displayMessage();
      assumeTrue(false);
    }
  }
  private void displayMessage()
  {
    if (!DISPLAYED)
    {
      DISPLAYED = true;
      String message = String.format(
          "This machine isn't configured to run machine_specific_tests.\n" + "To run these either\n"
              + "  1) Set machine_specific_tests.MachineSpecific.FORCE_RUN=true\n"
              + "  2) Add \"%s\" to machine_specific_tests.MachineSpecific.MACHINES",
          SystemUtils.getComputerName());
      SimpleLogger.warning(message);
    }
  }
}
