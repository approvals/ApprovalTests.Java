package org.approvaltests.reporters;

import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.namer.NamerWrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AutoApproveWhenEmptyReporterTest
{
  @Test
  void testWhenEmpty()
  {
    NamerWrapper namer = new NamerWrapper(Approvals.createApprovalNamer());
    FileUtils.delete(namer.getApprovalFile(".txt"));
    Approvals.verify("applesauce", new Options().withReporter(new AutoApproveWhenEmptyReporter()));
  }

  @Test
  void testWhenNotEmpty()
  {
    NamerWrapper namer = new NamerWrapper(Approvals.createApprovalNamer());
    UseReporterTest.TestReporter testReporter = new UseReporterTest.TestReporter();
    FileUtils.writeFile(namer.getApprovalFile(".txt"), "oldText");
    try
    {
      Approvals.verify("newText", new Options().withReporter(new AutoApproveWhenEmptyReporter(testReporter)));
    }
    catch (Throwable e)
    {
    }
    assertEquals("newText\n", UseReporterTest.TestReporter.getLast());
  }
}
