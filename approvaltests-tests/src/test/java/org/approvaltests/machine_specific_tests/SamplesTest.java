package org.approvaltests.machine_specific_tests;

import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

// begin-snippet: use_reporter_multiple
@UseReporter({DiffReporter.class, ClipboardReporter.class})
// end-snippet
public class SamplesTest extends MachineSpecificTest
{
  @Test
  public void testSwing()
  {
    TvGuide tv = new TvGuide();
    tv.selectTime("3pm");
    AwtApprovals.verify(tv);
  }
}
