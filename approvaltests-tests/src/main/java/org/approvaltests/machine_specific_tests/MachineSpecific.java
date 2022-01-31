package org.approvaltests.machine_specific_tests;

import com.spun.util.SystemUtils;

import java.util.Arrays;
import java.util.List;

public class MachineSpecific
{
  public static boolean       FORCE_RUN = false;
  private static List<String> MACHINES  = Arrays.asList("LLEWELLYN-FALCOs-MacBook-Pro-2.local",
      "llewelllcosmbp2.lan");
  public static boolean isMachineConfiguredForTesting()
  {
    return FORCE_RUN || MACHINES.contains(SystemUtils.getComputerName());
  }
}
