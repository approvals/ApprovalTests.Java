package org.approvaltests.reporters;

import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.namer.ApprovalNamer;
import org.junit.jupiter.api.Test;

class AutoApproveReporterTest
{
  @Test
  void testWhenEmpty()
  {
    ApprovalNamer namer = Approvals.createApprovalNamer();
    FileUtils.delete(namer.getApprovedFile(".txt"));
    Approvals.verify("applesauce", new Options().withReporter(new AutoApproveReporter()));
  }

  @Test
  void testWhenNotEmpty()
  {
    ApprovalNamer namer = Approvals.createApprovalNamer();
    FileUtils.writeFile(namer.getApprovedFile(".txt"), "oldText");
    Approvals.verify("newText", new Options().withReporter(new AutoApproveReporter()));
  }
}
