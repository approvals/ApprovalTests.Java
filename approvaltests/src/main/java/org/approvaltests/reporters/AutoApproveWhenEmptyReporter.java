package org.approvaltests.reporters;

import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.VerifyResult;

import java.io.File;

public class AutoApproveWhenEmptyReporter implements ReporterWithApprovalPower
{
  private final ApprovalFailureReporter reporter;
  private VerifyResult                  result;
  public AutoApproveWhenEmptyReporter()
  {
    this(Approvals.getReporter());
  }
  public AutoApproveWhenEmptyReporter(ApprovalFailureReporter reporter)
  {
    this.reporter = reporter;
  }
  @Override
  public boolean report(String received, String approved)
  {
    File a = new File(approved);
    if (!a.exists())
    {
      File r = new File(received);
      FileUtils.copyFile(r, a);
      result = VerifyResult.SUCCESS;
    }
    else
    {
      reporter.report(received, approved);
      result = VerifyResult.FAILURE;
    }
    return true;
  }
  @Override
  public VerifyResult approveWhenReported()
  {
    return result;
  }
}
