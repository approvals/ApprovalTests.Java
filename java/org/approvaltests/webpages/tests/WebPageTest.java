package org.approvaltests.webpages.tests;

import java.net.URI;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.webpages.WebPageApproval;
import org.approvaltests.webpages.WebPageChangeDetector;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class WebPageTest extends TestCase
{
  public void ptestWikipedia() throws Exception
  {
    WebPageApproval.verifyRenderedPage(new URI("http://cosmoquest.org"));
  }
  public void testChangeDetectorUI() throws Exception
  {
    Approvals.verify(new WebPageChangeDetector());
  }
}
