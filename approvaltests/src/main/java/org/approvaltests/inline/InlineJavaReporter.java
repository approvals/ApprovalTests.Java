package org.approvaltests.inline;

import com.spun.util.StringUtils;
import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalReporterWithCleanUp;
import org.approvaltests.namer.StackTraceNamer;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InlineJavaReporter implements ApprovalFailureReporter, ApprovalReporterWithCleanUp
{
  private final String                  sourceFilePath;
  private final StackTraceNamer         stackTraceNamer;
  private final ApprovalFailureReporter reporter;
  private final String                  additionalLines;
  public InlineJavaReporter(ApprovalFailureReporter reporter, boolean addApprovalLine)
  {
    this.reporter = reporter;
    this.stackTraceNamer = new StackTraceNamer();
    this.sourceFilePath = stackTraceNamer.getSourceFilePath();
    this.additionalLines = addApprovalLine ? "***** DELETE ME TO APPROVE *****" : "";
  }
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
  public String createReceived(String actual)
  {
    String file = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".java";
    String received = getReceivedFileName();
    String text = FileUtils.readFile(file);
    String fullText = createNewReceivedFileText(text, actual + additionalLines,
        this.stackTraceNamer.getInfo().getMethodName());
    FileUtils.writeFile(new File(received), fullText);
    return received;
  }
  private String getReceivedFileName()
  {
    return sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".received.txt";
  }
  public static String createNewReceivedFileText(String text, String actual, String methodName)
  {
    text = text.replaceAll("\r\n", "\n");
    CodeParts codeParts = CodeParts.splitCode(text, methodName);
    if (codeParts.method.contains("expected = \"\"\""))
    {
      replaceExpected(codeParts, actual);
    }
    else
    {
      addExpected(codeParts, actual);
    }
    return codeParts.getFullCode();
  }
  private static void addExpected(CodeParts codeParts, String actual)
  {
    int start = codeParts.method.indexOf("{") + 2;
    String before = codeParts.method.substring(0, start);
    String after = codeParts.method.substring(start);
    codeParts.method = before + getExpected(actual, codeParts.tab) + after;
  }
  private static String getExpected(String actual, String tab)
  {
    return String.format("%s%svar expected = \"\"\"\n%s%s%s%s\"\"\";\n", tab, tab, indent(actual, tab), tab, tab,
        tab);
  }
  private static void replaceExpected(CodeParts codeParts, String actual)
  {
    int start = codeParts.method.indexOf("expected = \"\"\"");
    start = codeParts.method.substring(0, start).lastIndexOf("\n") + 1;
    int end = codeParts.method.indexOf("\"\"\";");
    end = codeParts.method.indexOf("\n", end) + 1;
    String before = codeParts.method.substring(0, start);
    String after = codeParts.method.substring(end);
    codeParts.method = before + getExpected(actual, codeParts.tab) + after;
  }
  public static String indent(String actual, String tab)
  {
    String[] split = StringUtils.split(actual, "\n");
    String output = "";
    for (String line : split)
    {
      output += tab + tab + tab + line + "\n";
    }
    return output;
  }
  private static String extractLeadingWhitespace(String text)
  {
    Pattern pattern = Pattern.compile("^\\s+");
    Matcher matcher = pattern.matcher(text);
    if (matcher.find())
    { return matcher.group(); }
    return "\t";
  }
  @Override
  public void cleanUp(String received, String approved)
  {
    FileUtils.delete(getReceivedFileName());
  }
}
