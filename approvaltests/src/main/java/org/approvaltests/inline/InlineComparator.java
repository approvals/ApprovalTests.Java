package org.approvaltests.inline;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.core.Options;
import org.approvaltests.core.VerifyResult;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.StackTraceNamer;
import org.approvaltests.reporters.DiffMergeReporter;
import org.approvaltests.writers.ApprovalWriterFactory;
import org.lambda.functions.Function2;

import java.io.File;
import java.io.IOException;

public class InlineComparator
    implements
      Function2<File, File, VerifyResult>,
      ApprovalNamer,
      ApprovalWriter,
      ApprovalWriterFactory,
      ApprovalFailureReporter
{
  private final String          sourceFilePath;
  private final StackTraceNamer stackTraceNamer;
  private String                expected;
  private String                actual;
  private File                  approvedFile;
  private File                  receivedFile;
  public InlineComparator(String expected)
  {
    this.expected = expected;
    stackTraceNamer = new StackTraceNamer();
    sourceFilePath = stackTraceNamer.getSourceFilePath();
  }
  @Override
  public VerifyResult call(File received, File approved)
  {
    if (expected.equals(actual))
    {
      return VerifyResult.SUCCESS;
    }
    else
    {
      return VerifyResult.FAILURE;
    }
  }
  @Override
  public File getApprovedFile(String extensionWithDot)
  {
    if (approvedFile == null)
    {
      try
      {
        this.approvedFile = File.createTempFile("temp", extensionWithDot);
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
        this.receivedFile = File.createTempFile("temp", extensionWithDot);
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
  public File writeReceivedFile(File received)
  {
    FileUtils.writeFile(received, actual);
    return received;
  }
  @Override
  public String getFileExtensionWithDot()
  {
    return ".txt";
  }
  @Override
  public ApprovalWriter create(Object content, Options options)
  {
    this.actual = options.scrub("" + content);
    if (!actual.endsWith("\n"))
    {
      this.actual += "\n";
    }
    return this;
  }
  @Override
  public boolean report(String received, String approved)
  {
    DiffMergeReporter instance = DiffMergeReporter.INSTANCE;
    String file = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".java";
    String received1 = createReceived();
    return instance.report(received1, file);
  }
  private String createReceived()
  {
    String file = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".java";
    String received = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".received.txt";
    String text = FileUtils.readFile(file);
    String fullText = createNewReceivedFileText(text, this.actual, this.stackTraceNamer.getInfo().getMethodName());
    FileUtils.writeFile(new File(received), fullText);
    return received;
  }

  public static String createNewReceivedFileText(String text, String actual, String methodName) {
    int start = text.indexOf("void " + methodName + "(");
    start = text.indexOf("{", start);
    int next = text.indexOf("\n", start);
    int end = text.indexOf("}", next);
    int endString = text.indexOf("\"\"\";", next);
    String part1 = text.substring(0, next);
    String part2 = null;
    if (0 < endString && endString < end)
    {
      part2 = text.substring(endString + 4);
    }
    else
    {
      part2 = text.substring(next);
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
}
