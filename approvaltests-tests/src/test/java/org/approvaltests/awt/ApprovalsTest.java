package org.approvaltests.awt;

import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

@UseReporter({ClipboardReporter.class})
public class ApprovalsTest
{
  @Test
  void customPanel()
  {
    AwtApprovals.verify(new CustomPanel());
  }
}
