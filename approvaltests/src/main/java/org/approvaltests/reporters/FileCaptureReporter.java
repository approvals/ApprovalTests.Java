package org.approvaltests.reporters;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import org.apache.commons.lang.StringUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.lambda.functions.Function0;

import java.io.IOException;
import java.util.Arrays;

public class FileCaptureReporter implements ApprovalFailureReporter
{
  private String             message = "*** adding received file via FileCaptureReporter for further inspection";
  private Function0<Boolean> isGitRegistrationNeeded;
  public FileCaptureReporter()
  {
    this("*** adding received file via FileCaptureReporter for further inspection",
        () -> StringUtils.isNotEmpty(System.getenv("GITHUB_ACTIONS")));
  }
  public FileCaptureReporter(String message, Function0<Boolean> isGitRegistrationNeeded)
  {
    this.message = message;
    this.isGitRegistrationNeeded = isGitRegistrationNeeded;
  }
  @Override
  public void report(String received, String approved)
  {
    if (isGitRegistrationNeeded.call())
    {
      run("git", "config", "--local", "user.email", "action@github.com");
      run("git", "config", "--local", "user.name", "githubAction");
    }
    run("git", "add", "--force", received);
    run("git", "commit", "-m", "'" + message + "'");
    run("git", "push");
  }
  public static void run(String... command)
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
