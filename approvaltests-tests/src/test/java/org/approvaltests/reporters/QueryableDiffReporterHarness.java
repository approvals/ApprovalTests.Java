package org.approvaltests.reporters;

import com.spun.util.persistence.ExecutableCommand;

public class QueryableDiffReporterHarness implements ExecutableCommand
{
  private final GenericDiffReporter reporter;
  private final String              file1;
  private final String              file2;
  public QueryableDiffReporterHarness(GenericDiffReporter reporter, String file1, String file2)
  {
    this.reporter = reporter;
    this.file1 = file1;
    this.file2 = file2;
  }
  @Override
  public String getCommand()
  {
    String[] commandLine = reporter.getCommandLine("%s", "%s");
    return String.join(" ", commandLine);
  }
  @Override
  public String executeCommand(String command)
  {
    command = String.format(command, file1, file2);
    try
    {
      Runtime.getRuntime().exec(command);
    }
    catch (Throwable e)
    {
      return e.toString();
    }
    return command;
  }
}
