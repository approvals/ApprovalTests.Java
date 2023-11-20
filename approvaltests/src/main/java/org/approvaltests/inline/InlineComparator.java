package org.approvaltests.inline;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.Options;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.StackTraceNamer;

import java.io.File;
import java.io.IOException;

import static org.approvaltests.writers.Writer.approved;
import static org.approvaltests.writers.Writer.received;

public class InlineComparator implements ApprovalNamer, ApprovalFailureReporter
{
  private final String                  sourceFilePath;
  private final StackTraceNamer         stackTraceNamer;
  private String                        expected;
  private final ApprovalFailureReporter reporter;
  private File                          approvedFile;
  private File                          receivedFile;
  public InlineComparator(String expected, ApprovalFailureReporter reporter)
  {
    this.expected = expected;
    this.reporter = reporter;
    stackTraceNamer = new StackTraceNamer();
    sourceFilePath = stackTraceNamer.getSourceFilePath();
  }
  @Override
  public File getApprovedFile(String extensionWithDot)
  {
    if (approvedFile == null)
    {
      try
      {
        this.approvedFile = File.createTempFile("temp", approved + extensionWithDot);
        FileUtils.writeFile(approvedFile, expected);
      }
      catch (IOException e)
      {
        throw new RuntimeException(e);
      }
    }
    return approvedFile;
  }
  @Override
  public File getReceivedFile(String extensionWithDot)
  {
    if (receivedFile == null)
    {
      try
      {
        this.receivedFile = File.createTempFile("temp", received + extensionWithDot);
      }
      catch (IOException e)
      {
        throw new RuntimeException(e);
      }
    }
    return receivedFile;
  }
  @Override
  public ApprovalNamer addAdditionalInformation(String info)
  {
    return this;
  }
  @Override
  public String getApprovalName()
  {
    return "";
  }
  @Override
  public String getSourceFilePath()
  {
    return sourceFilePath;
  }
  @Override
  public boolean report(String received, String approved)
  {
    String sourceFile = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".java";
    String newSource = createReceived(FileUtils.readFile(received));
    return reporter.report(newSource, sourceFile);
  }
  private String createReceived(String actual)
  {
    String file = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".java";
    String received = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".received.txt";
    String text = FileUtils.readFile(file);
    String fullText = createNewReceivedFileText(text, actual, this.stackTraceNamer.getInfo().getMethodName());
    FileUtils.writeFile(new File(received), fullText);
    return received;
  }
  public static String createNewReceivedFileText(String text, String actual, String methodName)
  {
    text = text.replaceAll("\r\n", "\n");
    int start = text.indexOf("void " + methodName + "(");
    start = text.indexOf("{", start);
    int next = text.indexOf("\n", start);
    int end = text.indexOf("}", next);
    int endString = text.indexOf("\"\"\";", next);
    String part1 = text.substring(0, next);
    String part2 = null;
    if (0 < endString && endString < end)
    {
      // find next newline
      endString = text.indexOf("\n", endString);
      part2 = text.substring(endString + 1);
    }
    else
    {
      part2 = text.substring(next + 1);
    }
    String fullText = String.format("%s\n\t\tvar expected = \"\"\"\n%s\t\t\"\"\";\n%s", part1, indent(actual),
        part2);
    return fullText;
  }
  public static String indent(String actual)
  {
    String[] split = actual.split("\n");
    String output = "";
    for (String line : split)
    {
      output += "\t\t" + line + "\n";
    }
    return output;
  }
  public Options setForOptions(Options options)
  {
    if (reporter != null)
    {
      options = options.withReporter(this);
    }
    return options//
        .forFile().withNamer(this);
  }
}
