package org.approvaltests.reporters;

import java.io.IOException;
import java.util.Arrays;

import org.approvaltests.core.ApprovalFailureReporter;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;

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
    // TODO: continue here
    // checkGitUserConfigured();
    //        git config --local user.email "action@github.com"
    //        git config --local user.name "GitHub Action"
    run("git", "add", "--force", received);
    run("git", "commit", "-m", "'" + message + "'");
    run("git", "push");
  }
  private void run(String... command)
  {
    SimpleLogger.event(Arrays.toString(command));
    try
    {
      Process process = Runtime.getRuntime().exec(command);
      process.waitFor();
      final int exitValue = process.exitValue();
      if (exitValue != 0)
      {
        final String stdout = FileUtils.readStream(process.getInputStream());
        final String stderr = FileUtils.readStream(process.getErrorStream());
        SimpleLogger.warning(String.format("stdout:\n%s\nstderr:\n%s", stdout, stderr));
      }
    }
    catch (IOException | InterruptedException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
