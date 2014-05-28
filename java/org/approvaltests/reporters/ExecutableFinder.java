package org.approvaltests.reporters;

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
}