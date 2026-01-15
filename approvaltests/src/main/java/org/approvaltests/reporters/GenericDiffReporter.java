package org.approvaltests.reporters;

import com.spun.util.SystemUtils;
import com.spun.util.ThreadUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.core.ApprovalFailureReporter;
import org.lambda.query.Queryable;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenericDiffReporter implements ApprovalFailureReporter
{
  public static List<String> TEXT_FILE_EXTENSIONS  = Queryable.of(".txt", ".csv", ".htm", ".html", ".xml", ".eml",
      ".java", ".css", ".js", ".json", ".md", ".jsonl", ".yaml", ".yml", ".adoc", ".asciidoc");
  public static List<String> IMAGE_FILE_EXTENSIONS = Queryable.of(".png", ".gif", ".jpg", ".jpeg", ".bmp", ".tif",
      ".tiff");
  public static final String STANDARD_ARGUMENTS    = "%s %s";
  public static boolean      REPORT_MISSING_FILES  = false;
  protected String           diffProgram;
  protected String           arguments;
  protected String           diffProgramNotFoundMessage;
  private List<String>       validExtensions;
  public GenericDiffReporter(String diffProgram)
  {
    this(diffProgram, STANDARD_ARGUMENTS, "Couldn't find: " + diffProgram);
  }

  public GenericDiffReporter(String diffProgram, String diffProgramNotFoundMessage)
  {
    this(diffProgram, STANDARD_ARGUMENTS, diffProgramNotFoundMessage);
  }

  public GenericDiffReporter(String diffProgram, String argumentsFormat, String diffProgramNotFoundMessage)
  {
    this(diffProgram, argumentsFormat, diffProgramNotFoundMessage, TEXT_FILE_EXTENSIONS);
  }

  public GenericDiffReporter(String diffProgram, String argumentsFormat, String diffProgramNotFoundMessage,
      List<String> validFileExtensions)
  {
    this.diffProgram = diffProgram;
    this.arguments = argumentsFormat;
    this.diffProgramNotFoundMessage = diffProgramNotFoundMessage;
    validExtensions = validFileExtensions;
  }

  public GenericDiffReporter(DiffInfo info)
  {
    this(info.diffProgram, info.parameters,
        MessageFormat.format("Unable to find program at {0}", info.diffProgram), info.fileExtensions);
  }

  @Override
  public boolean report(String received, String approved)
  {
    if (!isWorkingInThisEnvironment(received))
    { return false; }
    FileUtils.createIfNeeded(approved);
    launch(received, approved);
    return true;
  }

  public boolean launch(String received, String approved)
  {
    try
    {
      ProcessBuilder builder = new ProcessBuilder(getCommandLine(received, approved));
      preventProcessFromClosing(builder);
      Process process = builder.start();
      processOutput(received, process);
      ThreadUtils.sleep(800); //Give program time to start
      boolean failed = !process.isAlive() && process.exitValue() != 0;
      return !failed;
    }
    catch (Exception e)
    {
      SimpleLogger.warning(e);
      return false;
    }
  }

  protected void processOutput(String received, Process process)
  {
  }

  protected void preventProcessFromClosing(ProcessBuilder builder)
  {
    if (!SystemUtils.isWindowsEnvironment())
    {
      File output = new File("/dev/null");
      builder.redirectError(output).redirectOutput(output);
    }
  }

  public String[] getCommandLine(String received, String approved)
  {
    String full = String.format(arguments, "{received}", "{approved}");
    List<String> argsSplitOnSpace = Arrays.stream(full.split(" "))
        .map(t -> t.replace("{received}", received).replace("{approved}", approved)).collect(Collectors.toList());
    ArrayList<String> commands = new ArrayList<String>();
    commands.add(diffProgram);
    commands.addAll(argsSplitOnSpace);
    return commands.toArray(new String[0]);
  }

  public boolean isWorkingInThisEnvironment(String forFile)
  {
    return checkFileExists() && isFileExtensionHandled(forFile);
  }

  public boolean checkFileExists()
  {
    boolean exists = new File(diffProgram).exists();
    if (REPORT_MISSING_FILES && !exists)
    {
      SimpleLogger.event(String.format("%s can't find '%s'", this.getClass().getSimpleName(), diffProgram));
    }
    return exists;
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

  @Override
  public String toString()
  {
    return getClass().getName();
  }
}
