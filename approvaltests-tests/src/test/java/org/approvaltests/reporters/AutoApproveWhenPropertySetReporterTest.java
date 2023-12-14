package org.approvaltests.reporters;

import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.namer.NamerWrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutoApproveWhenPropertySetReporterTest
{
  @Test
  void testWhenPropertyIsSet()
  {
    NamerWrapper namer = new NamerWrapper(Approvals.createApprovalNamer());
    cleanupFiles(namer);
    System.setProperty("org.approvaltests.reporters.autoapprove", "true");
    Approvals.verify("applesauce", new Options().withReporter(new AutoApproveWhenPropertySetReporter()));
    assertTrue(namer.getApprovalFile(".txt").exists());
    assertFalse(namer.getReceivedFile((".txt")).exists());
    cleanupFiles(namer);
  }
  @Test
  void testWhenPropertyNotSet()
  {
    System.clearProperty("org.approvaltests.reporters.autoapprove");
    NamerWrapper namer = new NamerWrapper(Approvals.createApprovalNamer());
    cleanupFiles(namer);
    try
    {
      Approvals.verify("applesauce",
          new Options().withReporter(new AutoApproveWhenPropertySetReporter(new UseReporterTest.TestReporter(),
              "org.approvaltests.reporters.autoapprove")));
    }
    catch (Throwable t)
    {
      assertTrue(t.getMessage().startsWith("Failed Approval"));
    }
    assertFalse(namer.getApprovalFile(".txt").exists());
    assertTrue(namer.getReceivedFile(".txt").exists());
    cleanupFiles(namer);
  }
  private static void cleanupFiles(NamerWrapper namer)
  {
    FileUtils.delete(namer.getApprovalFile(".txt"));
    FileUtils.delete(namer.getReceivedFile((".txt")));
  }
}
