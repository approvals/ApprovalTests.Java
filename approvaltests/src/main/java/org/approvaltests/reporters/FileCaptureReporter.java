package org.approvaltests.reporters;

import java.io.IOException;

import org.approvaltests.core.ApprovalFailureReporter;

import com.spun.util.ObjectUtils;

public class FileCaptureReporter implements ApprovalFailureReporter
{
  @Override
  public void report(String received, String approved)
  {
    run("git add --force " + received);
    // commit
    // push
  }
  private void run(String s)
  {
    try
    {
      Process command = Runtime.getRuntime().exec(s);
      command.waitFor();
    }
    catch (IOException | InterruptedException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
