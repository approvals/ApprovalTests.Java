package org.approvaltests.reporters;

import java.io.File;
import java.io.IOException;

import org.approvaltests.ReporterFactory;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalReporterWithCleanUp;

import com.spun.util.io.FileUtils;
import com.spun.util.persistence.ExecutableQuery;

public class ExecutableQueryFailure implements ApprovalFailureReporter, ApprovalReporterWithCleanUp
{
  private static final String   FILE_ADDITION = ".queryresults.txt";
  private final ExecutableQuery query;
  public ExecutableQueryFailure(ExecutableQuery query)
  {
    this.query = query;
  }
  public void report(String received, String approved) throws Exception
  {
    ApprovalFailureReporter reporter = ReporterFactory.get();
    reporter.report(runQueryAndGetPath(received), runQueryAndGetPath(approved));
    reporter.report(received, approved);
  }
  private String runQueryAndGetPath(String filename) throws IOException, Exception
  {
    if (!new File(filename).exists()) { return filename; }
    String newQuery = FileUtils.readFile(filename).trim();
    String newResult = query.executeQuery(newQuery);
    File newFile = new File(filename + FILE_ADDITION);
    String header = "\t\tDo NOT approve\n\t\tThis File will be Deleted\n\t\tit is for feedback purposes only\n";
    FileUtils.writeFile(newFile, String.format("%squery:\n%s\n\nresult:\n%s", header, newQuery, newResult));
    return newFile.getAbsolutePath();
  }
  @Override
  public void cleanUp(String received, String approved) throws Exception
  {
    new File(received + FILE_ADDITION).delete();
    new File(approved + FILE_ADDITION).delete();
  }
}
