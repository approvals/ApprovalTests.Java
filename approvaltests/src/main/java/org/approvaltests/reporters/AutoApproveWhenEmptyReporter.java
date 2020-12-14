package org.approvaltests.reporters;

import java.io.File;

import org.approvaltests.Approvals;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.VerifyResult;

import com.spun.util.io.FileUtils;

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
  public void report(String received, String approved)
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
  }
  @Override
  public VerifyResult approveWhenReported()
  {
    return result;
  }
}
