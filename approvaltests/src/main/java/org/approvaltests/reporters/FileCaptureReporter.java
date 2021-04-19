package org.approvaltests.reporters;

import java.io.IOException;

import org.approvaltests.core.ApprovalFailureReporter;

import com.spun.util.ObjectUtils;

public class FileCaptureReporter implements ApprovalFailureReporter
{
  private String message = "*** adding received file via FileCaptureReporter for further inspection";
  public FileCaptureReporter()
  {
  }
  public FileCaptureReporter(String message)
  {
    this.message = message;
  }
  @Override
  public void report(String received, String approved)
  {
    run("git add --force " + received);
    run(String.format("git commit -m '%s'", message));
    run("git push");
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
