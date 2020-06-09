package machine_specific_tests.approvaltests.tests;

import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.Test;

import machine_specific_tests.MachineSpecificTest;

// begin-snippet: use_reporter_multiple
@UseReporter({DiffReporter.class, ClipboardReporter.class})
// end-snippet 
public class Samples extends MachineSpecificTest
{
  @Test
  public void testSwing() throws Exception
  {
    TvGuide tv = new TvGuide();
    tv.selectTime("3pm");
    AwtApprovals.verify(tv);
  }
}
