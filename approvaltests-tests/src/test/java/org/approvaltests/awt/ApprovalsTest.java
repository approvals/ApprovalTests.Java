package org.approvaltests.awt;

import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

@UseReporter({ClipboardReporter.class})
public class ApprovalsTest
{
  @DisabledOnJre({JRE.JAVA_8})
  @Test
  void customPanel()
  {
    final CustomPanel panel = new CustomPanel();
    AwtApprovals.verify(panel);
  }
//  @DisabledOnJre({JRE.JAVA_8})
  @Disabled("continue here next week")
  @Test
  void customPanelWithText()
  {
    final CustomPanel panel = new CustomPanel(true, 20);
    AwtApprovals.verify(panel);
  }
  @EnabledOnJre({JRE.JAVA_8})
  @Test
  void customPanelOnJre8()
  {
    final CustomPanel panel = new CustomPanel();
    AwtApprovals.verify(panel);
  }
}
