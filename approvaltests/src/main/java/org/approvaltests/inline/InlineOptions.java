package org.approvaltests.inline;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.AutoApproveReporter;

public interface InlineOptions
{
  String DELETE_ME_TO_APPROVE = "***** DELETE ME TO APPROVE *****\n";
  String PREVIOUS_RESULT      = "vvvvv PREVIOUS RESULT      vvvvv\n";
  Options apply(Options options);
  public static InlineOptions showCode(boolean doShowCode)
  {
    if (doShowCode)
    {
      return options -> options.withReporter(new InlineJavaReporter(options.getReporter(), null));
    }
    else
    {
      return options -> options;
    }
  }
  public static InlineOptions automatic()
  {
    return options -> options.withReporter(new InlineJavaReporter(new AutoApproveReporter(), null));
  }
  public static InlineOptions semiAutomatic()
  {
    return options -> options
        .withReporter(new InlineJavaReporter(new AutoApproveReporter(), (x, y) -> DELETE_ME_TO_APPROVE));
  }
  public static InlineOptions semiAutomaticWithPreviousApproved()
  {
    return options -> options.withReporter(
        new InlineJavaReporter(new AutoApproveReporter(), InlineOptions::createPreviousCaptureFooter));
  }
  static String createPreviousCaptureFooter(String receivedPath, String approvedPath)
  {
    String approvedText = FileUtils.readFile((approvedPath));
    approvedText = approvedText.substring(0, approvedText.lastIndexOf("\n"));
    int previousResultIndex = approvedText.lastIndexOf(PREVIOUS_RESULT);
    if (previousResultIndex != -1)
    {
      approvedText = approvedText.substring(previousResultIndex + PREVIOUS_RESULT.length());
    }
    String receivedText = FileUtils.readFile((receivedPath));
    receivedText = receivedText.substring(0, receivedText.lastIndexOf("\n"));
    if (!receivedText.equals(approvedText))
    { return DELETE_ME_TO_APPROVE + PREVIOUS_RESULT + approvedText; }
    return "";
  }
}
