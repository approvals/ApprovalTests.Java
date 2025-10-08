package org.approvaltests.reporters;

import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.namer.NamerWrapper;
import org.junit.jupiter.api.Test;

class AutoApproveReporterTest
{
  @Test
  void testWhenEmpty()
  {
    NamerWrapper namer = new NamerWrapper(Approvals.createApprovalNamer());
    FileUtils.delete(namer.getApprovalFile(".txt"));
    Approvals.verify("applesauce", new Options().withReporter(new AutoApproveReporter()));
  }

  @Test
  void testWhenNotEmpty()
  {
    NamerWrapper namer = new NamerWrapper(Approvals.createApprovalNamer());
    FileUtils.writeFile(namer.getApprovalFile(".txt"), "oldText");
    Approvals.verify("newText", new Options().withReporter(new AutoApproveReporter()));
  }
}
