package machine_specific_tests;

import org.junit.Before;

import com.spun.util.SystemUtils;

public class MachineSpecificTest
{
  private static boolean DISPLAYED = false;
  @Before
  public void beforeMethod()
  {
    if (!MachineSpecific.isMachineConfiguredForTesting())
    {
      displayMessage();
      org.junit.Assume.assumeTrue(false);
    }
  }
  private void displayMessage()
  {
    if (!DISPLAYED)
    {
      DISPLAYED = true;
      String message = String.format(
          "This machine isn't configured to run machine_specific_tests.\n" + "To run these ethier\n"
              + "  1) Set machine_specific_tests.MachineSpecific.FORCE_RUN=true\n"
              + "  2) Add \"%s\" to machine_specific_tests.MachineSpecific.MACHINES",
          SystemUtils.getComputerName());
      System.out.println(message);
    }
  }
}
