package org.approvaltests.reporters;

import com.spun.util.persistence.ExecutableCommand;
import java.io.File;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalReporterWithCleanUp;
import org.approvaltests.core.Options;

import com.spun.util.io.FileUtils;

public class ExecutableQueryFailure implements ApprovalFailureReporter, ApprovalReporterWithCleanUp
{
  private static final String           FILE_ADDITION = ".queryresults.txt";
  private final ExecutableCommand       query;
  private final ApprovalFailureReporter reporter;
  private ExecutableQueryFailure(ExecutableCommand query, ApprovalFailureReporter reporter)
  {
    this.query = query;
    this.reporter = reporter;
  }
  public static Options create(ExecutableCommand query, Options options)
  {
    ExecutableQueryFailure executableQueryFailure = new ExecutableQueryFailure(query, options.getReporter());
    return options.withReporter(executableQueryFailure);
  }
  public void report(String received, String approved)
  {
    reporter.report(runQueryAndGetPath(received), runQueryAndGetPath(approved));
    reporter.report(received, approved);
  }
  private String runQueryAndGetPath(String filename)
  {
    if (!new File(filename).exists())
    { return filename; }
    String newQuery = FileUtils.readFile(filename).trim();
    String newResult = query.executeCommand(newQuery);
    File newFile = new File(filename + FILE_ADDITION);
    String header = "\t\tDo NOT approve\n\t\tThis File will be Deleted\n\t\tit is for feedback purposes only\n";
    FileUtils.writeFile(newFile, String.format("%squery:\n%s\n\nresult:\n%s", header, newQuery, newResult));
    return newFile.getAbsolutePath();
  }
  @Override
  public void cleanUp(String received, String approved)
  {
    new File(received + FILE_ADDITION).delete();
    new File(approved + FILE_ADDITION).delete();
  }
}
