package org.approvaltests.reporters;

import org.approvaltests.reporters.GenericDiffReporter;

import com.spun.util.persistence.ExecutableQuery;

public class QueryableDiffReporterHarness implements ExecutableQuery
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
  public String getQuery()
  {
    String[] commandLine = reporter.getCommandLine("%s", "%s");
    return String.join(" ", commandLine);
  }
  @Override
  public String executeQuery(String query)
  {
    query = String.format(query, file1, file2);
    try
    {
      Runtime.getRuntime().exec(query);
    }
    catch (Throwable e)
    {
      return e.toString();
    }
    return query;
  }
}
