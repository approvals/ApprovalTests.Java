package org.approvaltests.reporters;

import java.io.File;

import org.approvaltests.core.VerifyResult;

import com.spun.util.io.FileUtils;

public class AutoApproveReporter implements ReporterWithApprovalPower
{
  @Override
  public void report(String received, String approved)
  {
    File a = new File(approved);
    if (a.exists())
    {
      a.delete();
    }
    File r = new File(received);
    FileUtils.copyFile(r, a);
  }
  @Override
  public VerifyResult approveWhenReported()
  {
    return VerifyResult.SUCCESS;
  }
}
