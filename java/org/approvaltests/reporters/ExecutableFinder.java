package org.approvaltests.reporters;

import java.io.File;

public class ExecutableFinder
{
  private String diffProgram;
  private String diffProgramNotFoundMessage;
  public ExecutableFinder(String diffProgram, String diffProgramNotFoundMessage)
  {
    this.diffProgram = diffProgram;
    this.diffProgramNotFoundMessage = diffProgramNotFoundMessage;
  }
  public String getDiffProgram()
  {
    return diffProgram;
  }
  public String getDiffProgramNotFoundMessage()
  {
    return diffProgramNotFoundMessage;
  }
  protected boolean exists()
  {
    return new File(getDiffProgram()).exists();
  }
}