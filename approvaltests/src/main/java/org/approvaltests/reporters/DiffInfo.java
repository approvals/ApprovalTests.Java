package org.approvaltests.reporters;

import com.spun.util.ArrayUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class DiffInfo
{
  private static final String[] WINDOWS_PROGRAM_FILES = loadProgramFilesPaths();
  public String       diffProgram;
  public String       parameters;
  public List<String> fileExtensions;
  public DiffInfo(String diffProgram, String parameters, List<String> fileExtensions)
  {
    this.diffProgram = resolveWindowsPath(diffProgram);
    this.parameters = parameters;
    this.fileExtensions = fileExtensions;
  }
  public static DiffInfo getNull()
  {
    return new DiffInfo(null, null, null);
  }
  private static String resolveWindowsPath(String diffProgram)
  {
    diffProgram = diffProgram == null ? "" : diffProgram;
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
    return getFirstWorking(path, paths, "C:\\Program Files\\");
  }
  public DiffInfo(String diffProgram, List<String> fileExtensions)
  {
    this(diffProgram, GenericDiffReporter.STANDARD_ARGUMENTS, fileExtensions);
  }
  public static String getFirstWorking(String path, String[] paths, String ifNotFoundDefault)
  {
    String fullPath = ifNotFoundDefault + path;
    for (String p : paths)
    {
      fullPath = p + File.separatorChar + path;
      if (new File(fullPath).exists())
      {
        break;
      }
    }
    return fullPath;
  }
  public boolean isEmpty()
  {
    return "".equals(diffProgram);
  }
  public static String[] getProgramFilesPaths(){
    return WINDOWS_PROGRAM_FILES;
  }
  public static String[] loadProgramFilesPaths()
  {
    List<String> paths = new ArrayList<>();
    addIfNotNull("ProgramFiles(x86)", paths);
    addIfNotNull("ProgramFiles(x86)", paths);
    addIfNotNull("ProgramFiles", paths);
    addIfNotNull("ProgramW6432", paths);
    addIfNotNull("LOCALAPPDATA", paths, "\\Programs");
    return paths.toArray(new String[0]);
  }
  private static void addIfNotNull(String envVarName, List<String> paths)
  {
    addIfNotNull(envVarName, paths, "");
  }
  private static void addIfNotNull(String envVarName, List<String> paths, String postfix)
  {
    String path = System.getenv(envVarName);
    if (path != null)
    {
      paths.add(path + postfix);
    }
  }
  public static class One
  {
    public static String of(String pathOne, String... paths)
    {
      final String[] all = ArrayUtils.combine(pathOne, paths);
      for (String path : all)
      {
        if (new File(path).exists())
        { return path; }
      }
      return pathOne;
    }
  }
}
