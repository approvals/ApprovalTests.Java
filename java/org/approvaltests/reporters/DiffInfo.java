package org.approvaltests.reporters;

import java.io.File;
import java.util.HashSet;
import java.util.List;

public class DiffInfo
{
  public String       diffProgram;
  public String       parameters;
  public List<String> fileExtensions;
  public DiffInfo(String diffProgram, String parameters, List<String> fileExtensions)
  {
    this.diffProgram = resolveWindowsPath(diffProgram);
    this.parameters = parameters;
    this.fileExtensions = fileExtensions;
  }
  private static String resolveWindowsPath(String diffProgram)
  {
    String tag = "{ProgramFiles}";
    if (diffProgram.startsWith(tag))
    {
      diffProgram = getPathInProgramFilesX86(diffProgram.substring(tag.length()));
    }
    return diffProgram;
  }
  private static String getPathInProgramFilesX86(String path)
  {
    String[] paths = getProgramFilesPaths();
    return getFirstWorking(path, paths);
  }
  public DiffInfo(String diffProgram, List<String> fileExtensions)
  {
    this(diffProgram, GenericDiffReporter.STANDARD_ARGUMENTS, fileExtensions);
  }
  public static String getFirstWorking(String path, String[] paths)
  {
    String fullPath = path;
    for (String p : paths)
    {
      fullPath = p + File.pathSeparatorChar + path;
      if (new File(fullPath).exists())
      {
        break;
      }
    }
    return fullPath;
  }
  public static String[] getProgramFilesPaths()
  {
    HashSet<String> paths = new HashSet<String>();
    paths.add(System.getenv("ProgramFiles(x86)"));
    paths.add(System.getenv("ProgramFiles"));
    paths.add(System.getenv("ProgramW6432"));
    return paths.stream().filter(p -> p != null).toArray(s -> new String[s]);
  }
}
