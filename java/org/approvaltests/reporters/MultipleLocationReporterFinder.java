package org.approvaltests.reporters;

import java.io.File;
import java.util.Arrays;

public class MultipleLocationReporterFinder implements ReporterFinder
{

  private static final String NO_REPORTER_FOUND = "";
  private String reporterPath;
  private String[] possibles;

  public MultipleLocationReporterFinder(String[] possibleAbsolutePaths)
  {
    this.possibles = possibleAbsolutePaths;
    this.reporterPath = getFirstExistingReporter(possibleAbsolutePaths);
  }

  @Override
  public String fullPath()
  {
      return reporterPath;
  }

  @SuppressWarnings("unchecked")
  private static String getFirstExistingReporter(String[] possibles)
  {
    String path = NO_REPORTER_FOUND;
    for (int j = 0; j < possibles.length; j++) {
      if (new File(possibles[j]).exists()) {
        path = possibles[j];
      }
    }
    return path;
  }

  @Override
  public String notFoundMessage()
  {
    String locations = Arrays.asList(possibles).toString();
    return "Could not find any installed reporters. Tried all of: "+ locations ;
  }

  @Override
  public boolean exists()
  {
    return ! NO_REPORTER_FOUND.equals(reporterPath);
  }
  
}
