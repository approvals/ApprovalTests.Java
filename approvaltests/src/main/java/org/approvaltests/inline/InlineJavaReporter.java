package org.approvaltests.inline;

import com.spun.util.StringUtils;
import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalReporterWithCleanUp;
import org.approvaltests.namer.StackTraceNamer;
import org.lambda.functions.Function2;
import org.lambda.functions.Function3;

import java.io.File;

public class InlineJavaReporter implements ApprovalFailureReporter, ApprovalReporterWithCleanUp
{
  private final String                             sourceFilePath;
  private final StackTraceNamer                    stackTraceNamer;
  private Function2<String, String, String>        footerCreator;
  public ApprovalFailureReporter                   reporter;
  private String                                   additionalLines;
  public Function3<String, String, String, String> createNewReceivedFileText;
  public InlineJavaReporter(ApprovalFailureReporter reporter, Function2<String, String, String> footerCreator)
  {
    this.reporter = reporter;
    this.stackTraceNamer = new StackTraceNamer();
    this.sourceFilePath = stackTraceNamer.getSourceFilePath();
    this.createNewReceivedFileText = InlineJavaReporter::createNewReceivedFileText;
    if (footerCreator == null)
    {
      footerCreator = (source, actual) -> "";
    }
    this.footerCreator = footerCreator;
  }
  public String getSourceFilePath()
  {
    return sourceFilePath;
  }
  @Override
  public boolean report(String received, String approved)
  {
    additionalLines = footerCreator.call(received, approved);
    String sourceFile = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".java";
    String newSource = createReceived(FileUtils.readFile(received));
    return reporter.report(newSource, sourceFile);
  }
  public String createReceived(String actual)
  {
    String file = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".java";
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
  public static String createNewReceivedFileText(String javaSourceCode, String actual, String methodName)
  {
    javaSourceCode = javaSourceCode.replaceAll("\r\n", "\n");
    CodeParts codeParts = CodeParts.splitCode(javaSourceCode, methodName);
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
  @Override
  public void cleanUp(String received, String approved)
  {
    FileUtils.delete(getReceivedFileName());
  }
}
