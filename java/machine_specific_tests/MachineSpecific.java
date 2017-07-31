package machine_specific_tests;

import java.util.Arrays;
import java.util.List;

import com.spun.util.SystemUtils;

public class MachineSpecific
{
  public static boolean       FORCE_RUN = true;
  private static List<String> MACHINES  = Arrays.asList("LLEWELLYN-FALCOs-MacBook-Pro.local",
      "llewellalcosmbp.lan", "LLEWELLLCOsMBP2.lan");
  public static boolean isMachineConfiguredForTesting()
  {
    return FORCE_RUN || MACHINES.contains(SystemUtils.getComputerName());
  }
}
