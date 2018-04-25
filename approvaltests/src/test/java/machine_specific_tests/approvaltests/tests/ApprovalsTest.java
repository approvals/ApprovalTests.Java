package machine_specific_tests.approvaltests.tests;

import javax.swing.JButton;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.ImageReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.Test;

import machine_specific_tests.MachineSpecificTest;

@UseReporter({ImageReporter.class, ClipboardReporter.class})
public class ApprovalsTest extends MachineSpecificTest
{
  @Test
  public void testApproveComponent() throws Exception
  {
    JButton b = new JButton("Approval Tests Rule");
    b.setSize(150, 20);
    Approvals.verify(b);
  }
  @Test
  public void testTvGuide() throws Exception
  {
    TvGuide tv = new TvGuide();
    tv.selectTime("3pm");
    Approvals.verify(tv);
  }
}
