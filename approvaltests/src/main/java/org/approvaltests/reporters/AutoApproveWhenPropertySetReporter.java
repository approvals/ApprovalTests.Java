package org.approvaltests.reporters;

import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.VerifyResult;

import java.io.File;

public class AutoApproveWhenPropertySetReporter implements ReporterWithApprovalPower
{
  private final ApprovalFailureReporter reporter;
  private final String                  property;
  private VerifyResult                  result;
  public AutoApproveWhenPropertySetReporter()
  {
    this(Approvals.getReporter(), "org.approvaltests.reporters.autoapprove");
  }
  public AutoApproveWhenPropertySetReporter(ApprovalFailureReporter reporter, String property)
  {
    this.reporter = reporter;
    this.property = property;
  }
  @Override
  public boolean report(String received, String approved)
  {
    String v = System.getProperty(property);
    if ("".equals(v) || "true".equalsIgnoreCase(v))
    {
      FileUtils.copyFile(new File(received), new File(approved));
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
