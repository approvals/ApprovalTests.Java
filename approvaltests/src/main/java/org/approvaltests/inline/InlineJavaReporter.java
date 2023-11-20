package org.approvaltests.inline;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.namer.StackTraceNamer;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InlineJavaReporter implements ApprovalFailureReporter
{
  private final String                  sourceFilePath;
  private final StackTraceNamer         stackTraceNamer;
  private final ApprovalFailureReporter reporter;
  public InlineJavaReporter(ApprovalFailureReporter reporter)
  {
    this.reporter = reporter;
    this.stackTraceNamer = new StackTraceNamer();
    this.sourceFilePath = stackTraceNamer.getSourceFilePath();
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
    int startOfLine = text.substring(0, start).lastIndexOf("\n") + 1;
    String line = text.substring(startOfLine, start);
    String tab = extractLeadingWhitespace(line);

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
    String fullText = String.format("%s\n%s%svar expected = \"\"\"\n%s%s%s\"\"\";\n%s", part1, tab, tab,
        indent(actual, tab), tab, tab, part2);
    return fullText;
  }
  public static String indent(String actual, String tab)
  {
    String[] split = actual.split("\n");
    String output = "";
    for (String line : split)
    {
      output += tab + tab + line + "\n";
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
}
