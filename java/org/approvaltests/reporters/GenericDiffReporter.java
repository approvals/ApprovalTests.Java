package org.approvaltests.reporters;

import java.util.Arrays;
import java.util.List;

import com.spun.util.io.FileUtils;

public class GenericDiffReporter implements EnvironmentAwareReporter
{
  public static final String WINDOWS_ARGUMENT_FORMAT = "\"%s\" \"%s\"";
  protected String           arguments;
  final ReporterFinder reporterFinder;
  private List<String>       validExtensions;
  public static List<String> TEXT_FILE_EXTENSIONS  = Arrays.asList(".txt", ".csv", ".htm", ".html", ".xml",
                                                       ".eml", ".java", ".css", ".js");
  public static List<String> IMAGE_FILE_EXTENSIONS = Arrays.asList(".png", ".gif", ".jpg", ".jpeg", ".bmp",
                                                       ".tif", ".tiff");
  public GenericDiffReporter(String diffProgram, String diffProgramNotFoundMessage)
  {
    this(diffProgram, WINDOWS_ARGUMENT_FORMAT, diffProgramNotFoundMessage);
  }
  private GenericDiffReporter(String diffProgram, String argumentsFormat, String diffProgramNotFoundMessage)
  {
    this(diffProgram, argumentsFormat, diffProgramNotFoundMessage, TEXT_FILE_EXTENSIONS);
  }
  public GenericDiffReporter(String diffProgram, String argumentsFormat, String diffProgramNotFoundMessage,
      List<String> validFileExtensions)
  {
    reporterFinder = new SingleLocationReporterFinder(diffProgram, diffProgramNotFoundMessage);
    this.arguments = argumentsFormat;
    validExtensions = validFileExtensions;
  }
  public GenericDiffReporter(String[] possibleLocations, String argumentFormat)
  {
    reporterFinder = new MultipleLocationReporterFinder(possibleLocations);
    arguments = argumentFormat;
    validExtensions = TEXT_FILE_EXTENSIONS;
  }
  @Override
  public void report(String received, String approved) throws Exception
  {
    if (!isWorkingInThisEnvironment(received)) { throw new RuntimeException(reporterFinder.notFoundMessage()); }
    FileUtils.createIfNeeded(approved);
    Process exec = Runtime.getRuntime().exec(getCommandLine(received, approved));
    //    int waitFor = exec.waitFor();
    //    String text = DatabaseLifeCycleUtils.extractText(exec.getErrorStream());
    //    System.out.println(waitFor + text);
  }
  public String getCommandLine(String received, String approved)
  {
    String command = "%s " + arguments;
    command = String.format(command, reporterFinder.fullPath(), received, approved);
    System.out.println(command);
    return command;
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    return reporterFinder.exists() && isFileExtensionHandled(forFile);
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
