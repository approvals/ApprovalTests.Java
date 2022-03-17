package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Rectangle;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class ApprovalsTest
{
  @Test
  public void testToString()
  {
    Approvals.verify(new Rectangle(5, 10, 100, 200));
  }
  @Test
  public void testAsJson()
  {
    JsonApprovals.verifyAsJson(new Rectangle(5, 10, 100, 200));
  }
  @Test
  public void testWrongCall()
  {
    Approvals.verifyException(() -> {
      JButton b = new JButton("Approval Tests Rule");
      b.setPreferredSize(new Dimension(150, 20));
      Approvals.verify(b, new Options(new QuietReporter()));
    });
  }
  @Test
  void verifyAllTemplate()
  {
    // begin-snippet: VerifyAllStartingPoint
    String[] inputs = {"input.value1", "input.value2"};
    Approvals.verifyAll("TITLE", inputs, s -> "placeholder " + s);
    // end-snippet
  }
}
