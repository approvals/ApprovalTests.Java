package org.approvaltests;

import java.awt.Rectangle;

import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class ApprovalsTest
{
  @Test
  public void testToString() throws Exception
  {
    Approvals.verify(new Rectangle(5, 10, 100, 200));
  }
  @Test
  public void testAsJson() throws Exception
  {
    Approvals.verifyAsJson(new Rectangle(5, 10, 100, 200));
  }
}
