package org.approvaltests.reporters;

import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.namer.ApprovalNamer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AutoApproveWhenEmptyReporterTest
{
  @Test
  void testWhenEmpty()
  {
    ApprovalNamer namer = Approvals.createApprovalNamer();
    FileUtils.delete(namer.getApprovedFile(".txt"));
    Approvals.verify("applesauce", new Options().withReporter(new AutoApproveWhenEmptyReporter()));
  }

  @Test
  void testWhenNotEmpty()
  {
    ApprovalNamer namer = Approvals.createApprovalNamer();
    UseReporterTest.TestReporter testReporter = new UseReporterTest.TestReporter();
    FileUtils.writeFile(namer.getApprovedFile(".txt"), "oldText");
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
