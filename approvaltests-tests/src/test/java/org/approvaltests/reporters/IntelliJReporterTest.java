package org.approvaltests.reporters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.approvaltests.reporters.intellij.IntelliJReporter;
import org.junit.jupiter.api.Test;

public class IntelliJReporterTest
{
  @Test
  public void testFindJetBrainsIdes()
  {
    String[] commands = {"C:\\Program Files\\JetBrains\\PyCharm 2025.1.1.1\\bin\\pycharm64.exe"};
    String result = IntelliJReporter.findJetBrainsIdes(commands);
    assertEquals(commands[0], result);
  }

  @Test
  public void testFindJetBrainsIdesLinux()
  {
    String[] commands = {"/opt/JetBrains/PyCharm 2025.1.1.1/bin/pycharm64.exe"};
    String result = IntelliJReporter.findJetBrainsIdes(commands);
    assertEquals(commands[0], result);
  }
}
