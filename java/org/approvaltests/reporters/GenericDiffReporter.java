package org.approvaltests.reporters;

import java.util.Arrays;
import java.util.List;

import com.spun.util.io.FileUtils;

public class GenericDiffReporter implements EnvironmentAwareReporter
{
  protected String           arguments;
  final ExecutableFinder executableFinder;
  private List<String>       validExtensions;
  public static List<String> TEXT_FILE_EXTENSIONS  = Arrays.asList(".txt", ".csv", ".htm", ".html", ".xml",
                                                       ".eml", ".java", ".css", ".js");
  public static List<String> IMAGE_FILE_EXTENSIONS = Arrays.asList(".png", ".gif", ".jpg", ".jpeg", ".bmp",
                                                       ".tif", ".tiff");
  public GenericDiffReporter(String diffProgram, String diffProgramNotFoundMessage)
  {
    this(diffProgram, "\"%s\" \"%s\"", diffProgramNotFoundMessage);
  }
  private GenericDiffReporter(String diffProgram, String argumentsFormat, String diffProgramNotFoundMessage)
  {
    this(diffProgram, argumentsFormat, diffProgramNotFoundMessage, TEXT_FILE_EXTENSIONS);
  }
  public GenericDiffReporter(String diffProgram, String argumentsFormat, String diffProgramNotFoundMessage,
      List<String> validFileExtensions)
  {
    executableFinder = new ExecutableFinder(diffProgram, diffProgramNotFoundMessage);
    this.arguments = argumentsFormat;
    validExtensions = validFileExtensions;
  }
  @Override
  public void report(String received, String approved) throws Exception
  {
    if (!isWorkingInThisEnvironment(received)) { throw new RuntimeException(executableFinder.getDiffProgramNotFoundMessage()); }
    FileUtils.createIfNeeded(approved);
    Process exec = Runtime.getRuntime().exec(getCommandLine(received, approved));
    //    int waitFor = exec.waitFor();
    //    String text = DatabaseLifeCycleUtils.extractText(exec.getErrorStream());
    //    System.out.println(waitFor + text);
  }
  public String getCommandLine(String received, String approved)
  {
    String command = "%s " + arguments;
    command = String.format(command, executableFinder.getDiffProgram(), received, approved);
    System.out.println(command);
    return command;
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    return executableFinder.exists() && isFileExtensionHandled(forFile);
  }
  public boolean isFileExtensionHandled(String forFile)
  {
    return isFileExtensionValid(forFile, validExtensions);
  }
  public static boolean isFileExtensionValid(String forFile, List<String> validExtensionsWithDot)
  {
    String extensionWithDot = FileUtils.getExtensionWithDot(forFile);
    return validExtensionsWithDot.contains(extensionWithDot);
  }
}
