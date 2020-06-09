package machine_specific_tests.approvaltests.webpages.tests;

import java.net.URI;

import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.webpages.WebPageApproval;
import org.approvaltests.webpages.WebPageChangeDetector;
import org.junit.Test;

import machine_specific_tests.MachineSpecificTest;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class WebPageTest extends MachineSpecificTest
{
  //@Test
  public void testWikipedia() throws Exception
  {
    WebPageApproval.verifyRenderedPage(new URI("http://cosmoquest.org"));
  }
  @Test
  public void testChangeDetectorUI() throws Exception
  {
    AwtApprovals.verify(new WebPageChangeDetector().gui);
  }
}
