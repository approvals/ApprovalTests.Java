package org.approvaltests.machine_specific_tests.approvaltests.tests;

import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.machine_specific_tests.MachineSpecificTest;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.Test;

// begin-snippet: use_reporter_multiple
@UseReporter({DiffReporter.class, ClipboardReporter.class})
// end-snippet 
public class SamplesTest extends MachineSpecificTest
{
  @Test
  public void testSwing() throws Exception
  {
    TvGuide tv = new TvGuide();
    tv.selectTime("3pm");
    AwtApprovals.verify(tv);
  }
}
