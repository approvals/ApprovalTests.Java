package machine_specific_tests.approvaltests.webpages.tests;

import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.machine_specific_tests.MachineSpecificTest;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.webpages.WebPageApproval;
import org.approvaltests.webpages.WebPageChangeDetector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.URI;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class WebPageTest extends MachineSpecificTest
{
  @Disabled("run manually, todo: create snippet")
  @Test
  public void testWikipedia() throws Exception
  {
    WebPageApproval.verifyRenderedPage(new URI("http://cosmoquest.org"));
  }
  @Test
  public void testChangeDetectorUI()
  {
    AwtApprovals.verify(new WebPageChangeDetector().gui);
  }
}
