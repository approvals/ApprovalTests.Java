package org.approvaltests.reporters;

import java.util.List;

public class DiffInfo
{
  public String       diffProgram;
  public String       parameters;
  public List<String> fileExtensions;
  public DiffInfo(String diffProgram, String parameters, List<String> fileExtensions)
  {
    this.diffProgram = diffProgram;
    this.parameters = parameters;
    this.fileExtensions = fileExtensions;
  }
  public DiffInfo(String diffProgram, List<String> fileExtensions)
  {
    this(diffProgram, GenericDiffReporter.STANDARD_ARGUMENTS, fileExtensions);
  }
}
