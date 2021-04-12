package org.approvaltests.awt;

import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

@UseReporter({ClipboardReporter.class})
public class ApprovalsTest
{
  @EnabledOnJre({JRE.JAVA_11, JRE.JAVA_16, JRE.JAVA_15, JRE.JAVA_9, JRE.JAVA_10})
  @Test
  void customPanel()
  {
    final CustomPanel panel = new CustomPanel();
    AwtApprovals.verify(panel);
  }
}
