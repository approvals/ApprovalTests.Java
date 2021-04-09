package org.approvaltests.machine_specific_tests;

import java.awt.Dimension;

import javax.swing.JButton;

import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.ImageReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

@UseReporter({ClipboardReporter.class})
public class ApprovalsTest extends MachineSpecificTest
{
  @Test
  public void testApproveComponent()
  {
    JButton b = new JButton("Approval Tests Rule");
    b.setPreferredSize(new Dimension(150, 20));
    AwtApprovals.verify(b);
  }
  @Test
  public void testTvGuide()
  {
    TvGuide tv = new TvGuide();
    tv.selectTime("3pm");
    AwtApprovals.verify(tv);
  }

  @Test
  void customPanel() {
    AwtApprovals.verify(new CustomPanel());
  }
}
