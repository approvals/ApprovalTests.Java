package org.approvaltests.reporters;

import java.io.File;

public class SingleLocationReporterFinder implements ReporterFinder
{
  private String diffProgram;
  private String diffProgramNotFoundMessage;
  public SingleLocationReporterFinder(String diffProgram, String diffProgramNotFoundMessage)
  {
    this.diffProgram = diffProgram;
    this.diffProgramNotFoundMessage = diffProgramNotFoundMessage;
  }
  public String fullPath()
  {
    return diffProgram;
  }
  
  @Override
  public String notFoundMessage()
  {
    return diffProgramNotFoundMessage;
  }
  
  @Override
  public boolean exists()
  {
    return new File(fullPath()).exists();
  }
}