package org.approvaltests.reporters;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.lambda.functions.Function1;
import org.lambda.query.extensions.Queryyy;

public class MultipleLocationReporterFinder implements ReporterFinder
{

  private String reporterPath;

  public MultipleLocationReporterFinder(String[] possibles)
  {
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
//    List<String> existing = possibles.use(Queryyy.class).where(new Function1<String, Boolean>()
//    {
//
//      @Override
//      public Boolean call(String path)
//      {
//        return new File(path).exists();
//      }
//      
//    });
//    return existing.size() == 0 ? "" : existing.get(0);
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean exists()
  {
    // TODO Auto-generated method stub
    return false;
  }
  
}
