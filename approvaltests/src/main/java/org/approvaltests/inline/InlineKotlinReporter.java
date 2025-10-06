package org.approvaltests.inline;

import com.spun.util.ArrayUtils;
import com.spun.util.StringUtils;
import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalReporterWithCleanUp;
import org.approvaltests.namer.StackTraceNamer;
import org.lambda.functions.Function2;
import org.lambda.functions.Function3;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InlineKotlinReporter implements ApprovalFailureReporter, ApprovalReporterWithCleanUp
{
  private final String                              sourceFilePath;
  private final StackTraceNamer                     stackTraceNamer;
  private final Function2<String, String, String>   footerCreator;
  private final ApprovalFailureReporter             reporter;
  private String                                    additionalLines;
  private Function3<String, String, String, String> createNewReceivedFileText;
  public InlineKotlinReporter(ApprovalFailureReporter reporter)
  {
    this(reporter, null);
  }
  public InlineKotlinReporter(ApprovalFailureReporter reporter, Function2<String, String, String> footerCreator)
  {
    this.reporter = reporter;
    this.footerCreator = footerCreator != null ? footerCreator : (source, actual) -> "";
    Result result = getResult();
    this.stackTraceNamer = result.stackTraceNamer;
    this.sourceFilePath = result.sourceFilePath;
    this.createNewReceivedFileText = result.createNewReceivedFileText;
  }
  public static Result getResult()
  {
    StackTraceNamer stackTraceNamer = new StackTraceNamer();
    String sourceFilePath = stackTraceNamer.getSourceFilePath();
    Function3<String, String, String, String> createNewReceivedFileText = (kotlinSourceCode, actual,
        methodName) -> createNewReceivedFileText(kotlinSourceCode, actual, methodName);
    return new Result(stackTraceNamer, sourceFilePath, createNewReceivedFileText);
  }
  public static class Result
  {
    public final StackTraceNamer                           stackTraceNamer;
    public final String                                    sourceFilePath;
    public final Function3<String, String, String, String> createNewReceivedFileText;
    public Result(StackTraceNamer stackTraceNamer, String sourceFilePath,
        Function3<String, String, String, String> createNewReceivedFileText)
    {
      this.stackTraceNamer = stackTraceNamer;
      this.sourceFilePath = sourceFilePath;
      this.createNewReceivedFileText = createNewReceivedFileText;
    }
    public boolean isKotlin()
    {
      String sourceFile = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".kt";
      return Files.exists(Paths.get(sourceFile));
    }
  }
  @Override
  public boolean report(String received, String approved)
  {
    additionalLines = footerCreator.call(received, approved);
    String sourceFile = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".kt";
    String newSource = createReceived(FileUtils.readFile(received));
    return reporter.report(newSource, sourceFile);
  }
  public String createReceived(String actual)
  {
    String file = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".kt";
    String received = getReceivedFileName();
    String text = FileUtils.readFile(file);
    String fullText = this.createNewReceivedFileText.call(text, actual + additionalLines,
        this.stackTraceNamer.getInfo().getMethodName());
    FileUtils.writeFile(new File(received), fullText);
    return received;
  }
  private String getReceivedFileName()
  {
    return sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".received.txt";
  }
  @Override
  public void cleanUp(String received, String approved)
  {
    FileUtils.delete(getReceivedFileName());
  }
  public static String createNewReceivedFileText(String kotlinSourceCode, String actual, String methodName)
  {
    String normalizedSourceCode = kotlinSourceCode.replaceAll("\r\n", "\n");
    CodeParts codeParts = splitCode(normalizedSourceCode, methodName);
    if (codeParts.method != null && codeParts.method.contains("expected = \"\"\""))
    {
      replaceExpected(codeParts, actual);
    }
    else
    {
      addExpected(codeParts, actual);
    }
    return codeParts.getFullCode();
  }
  private static CodeParts splitCode(String text, String methodName)
  {
    CodeParts codeParts = new CodeParts();
    String[] lines = text.split("\n");
    // Remove empty trailing elements
    int actualLength = lines.length;
    while (actualLength > 0 && lines[actualLength - 1].isEmpty())
    {
      actualLength--;
    }
    String[] trimmedLines = new String[actualLength];
    System.arraycopy(lines, 0, trimmedLines, 0, actualLength);
    lines = trimmedLines;
    int start = 0;
    int end = 0;
    for (int i = 0; i < lines.length; i++)
    {
      String line = lines[i];
      if (start == 0)
      {
        // Do a regex search to check if a line contains the text "fun"
        if (line.matches(".*fun\\s+" + methodName + "\\s*\\(.*"))
        {
          start = i;
          codeParts.tab = extractLeadingWhitespace(line);
        }
      }
      else if (end == 0)
      {
        if (line.startsWith(codeParts.tab + "}"))
        {
          end = i + 1;
          break;
        }
      }
    }
    codeParts.before = String.join("\n", ArrayUtils.getSubsection(lines, 0, start));
    codeParts.method = String.join("\n", ArrayUtils.getSubsection(lines, start, end));
    codeParts.after = String.join("\n", ArrayUtils.getSubsection(lines, end, lines.length));
    return codeParts;
  }
  private static String extractLeadingWhitespace(String text)
  {
    Pattern pattern = Pattern.compile("^\\s+");
    Matcher matcher = pattern.matcher(text);
    if (matcher.find())
    { return matcher.group(); }
    return "\t";
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
    return String.format("%s%sval expected = \"\"\"\n%s%s%s%s\"\"\".trimIndent()\n", tab, tab, indent(actual, tab),
        tab, tab, tab);
  }
  private static void replaceExpected(CodeParts codeParts, String actual)
  {
    int start = codeParts.method.indexOf("expected = \"\"\"");
    start = codeParts.method.substring(0, start).lastIndexOf("\n") + 1;
    // Find the closing """ by looking for it at the start of a line (after whitespace)
    int searchPos = start + "expected = \"\"\"".length();
    int end = -1;
    while (searchPos < codeParts.method.length())
    {
      int nextTripleQuote = codeParts.method.indexOf("\"\"\"", searchPos);
      if (nextTripleQuote == -1)
        break;
      // Check if this """ is at the start of a line (preceded only by whitespace)
      int lineStart = codeParts.method.lastIndexOf("\n", nextTripleQuote - 1) + 1;
      String textBeforeQuote = codeParts.method.substring(lineStart, nextTripleQuote);
      if (textBeforeQuote.trim().isEmpty())
      {
        end = nextTripleQuote;
        break;
      }
      searchPos = nextTripleQuote + 1;
    }
    if (end == -1)
    {
      // Fallback to old behavior if we can't find it
      end = codeParts.method.indexOf("\"\"\"", start + "expected = \"\"\"".length());
    }
    end += 3; // Move past the closing """
    // Check if there's a .trimIndent() after the closing """
    String afterTripleQuote = codeParts.method.substring(end);
    if (afterTripleQuote.startsWith(".trimIndent()"))
    {
      end += ".trimIndent()".length();
    }
    end = codeParts.method.indexOf("\n", end) + 1;
    String before = codeParts.method.substring(0, start);
    String after = codeParts.method.substring(end);
    codeParts.method = before + getExpected(actual, codeParts.tab) + after;
  }
  private static String indent(String actual, String tab)
  {
    String[] split = StringUtils.split(actual, "\n");
    StringBuilder output = new StringBuilder();
    for (String line : split)
    {
      output.append(tab).append(tab).append(tab).append(line).append("\n");
    }
    return output.toString();
  }
}
