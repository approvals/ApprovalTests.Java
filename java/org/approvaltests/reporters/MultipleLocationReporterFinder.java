package org.approvaltests.reporters;

import java.io.File;
import java.util.Arrays;

public class MultipleLocationReporterFinder implements ReporterFinder
{

  private String reporterPath;
  private String[] possibles;

  public MultipleLocationReporterFinder(String[] possibles)
  {
    this.possibles = possibles;
    this.reporterPath = getFirstExistingReporter(possibles);
  }

  @Override
  public String fullPath()
  {
      return reporterPath;
  }

  @SuppressWarnings("unchecked")
  private static String getFirstExistingReporter(String[] possibles)
  {
    String path = "";
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
    // TODO Auto-generated method stub
    return false;
  }
  
}
