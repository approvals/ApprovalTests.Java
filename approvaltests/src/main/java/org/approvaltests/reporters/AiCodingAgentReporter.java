package org.approvaltests.reporters;

import com.spun.util.SystemUtils;
import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AiCodingAgentReporter implements ApprovalFailureReporter
{
  public static final AiCodingAgentReporter INSTANCE              = new AiCodingAgentReporter();
  public static List<String>                ENVIRONMENT_VARIABLES = new ArrayList<>(Arrays.asList(
      "AI_AGENT",
      "APPROVAL_TESTS_AGENT",
      "CLAUDECODE",
      "CLAUDE_CODE_ENTRYPOINT",
      "CODEX_SANDBOX",
      "CURSOR_AGENT",
      "CURSOR_TRACE_ID",
      "GEMINI_CLI",
      "OPENCODE"));
  private static final List<String>         FALSE_VALUES          = Arrays
      .asList("0", "f", "F", "false", "FALSE", "False");
  private final Function1<String, String>   environment;
  public AiCodingAgentReporter()
  {
    this(System::getenv);
  }

  public AiCodingAgentReporter(Map<String, String> environment)
  {
    this(environment::get);
  }

  public AiCodingAgentReporter(Function1<String, String> environment)
  {
    this.environment = environment;
  }

  @Override
  public boolean report(String received, String approved)
  {
    if (!isAiCodingAgentDetected())
    { return false; }
    System.out.println(getReport(received, approved, SystemUtils.isWindowsEnvironment()));
    return true;
  }

  public boolean isAiCodingAgentDetected()
  {
    return Queryable.of(ENVIRONMENT_VARIABLES).any(v -> isSet(environment.call(v)));
  }

  private static boolean isSet(String value)
  {
    return value != null && !value.isBlank() && !FALSE_VALUES.contains(value.trim());
  }

  public static String getReport(String received, String approved, boolean windows)
  {
    String receivedPath = new File(received).getAbsolutePath();
    String approvedPath = new File(approved).getAbsolutePath();
    StringBuilder b = new StringBuilder();
    b.append("# APPROVAL TEST FAILED\n");
    b.append("    approved: " + approvedPath + "\n");
    b.append("    received: " + receivedPath + "\n");
    b.append(
        "    approve_with: " + ClipboardReporter.getAcceptApprovalText(receivedPath, approvedPath, windows)
            + "\n");
    b.append("\n");
    appendFile(b, "APPROVED", approvedPath);
    b.append("\n");
    appendFile(b, "RECEIVED", receivedPath);
    b.append("----------------------");
    return b.toString();
  }

  private static void appendFile(StringBuilder b, String label, String path)
  {
    b.append("    ## " + label + "\n");
    b.append("    ```" + getExtension(path) + "\n");
    for (String line : readContent(label, path).split("\n", -1))
    {
      b.append("    " + line + "\n");
    }
    b.append("    ```\n");
  }

  private static String readContent(String label, String path)
  {
    try
    {
      File file = new File(path);
      if (!file.exists())
      { return "** Error reading " + label + " file **"; }
      String text = FileUtils.readFile(file).replace("\r\n", "\n");
      return text.endsWith("\n") ? text.substring(0, text.length() - 1) : text;
    }
    catch (Throwable e)
    {
      return "** Error reading " + label + " file **";
    }
  }

  private static String getExtension(String path)
  {
    String name = new File(path).getName();
    int dot = name.lastIndexOf('.');
    return dot < 0 ? "" : name.substring(dot + 1);
  }
}
