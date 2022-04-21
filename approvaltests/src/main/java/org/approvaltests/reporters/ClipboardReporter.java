package org.approvaltests.reporters;

import com.spun.util.SystemUtils;
import com.spun.util.WindowUtils;

import java.awt.GraphicsEnvironment;
import java.io.File;

/**
 * A getReporter which creates the command to accept the received
 * file as the approve file and copies it to the clipboard:<br>
 * <code>move received.txt approved.txt</code>
 */
public class ClipboardReporter implements EnvironmentAwareReporter
{
  @Override
  public void report(String received, String approved)
  {
    WindowUtils.copyToClipBoard(getCommandLine(received, approved), false);
  }
  public static String getCommandLine(String received, String approved)
  {
    File r = new File(received);
    File a = new File(approved);
    String commandLine = ClipboardReporter.getAcceptApprovalText(r.getAbsolutePath(), a.getAbsolutePath());
    return commandLine;
  }
  /**
   * The clipboard will not be available in a headless environment.
   */
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    return !GraphicsEnvironment.isHeadless();
  }
  public static String getAcceptApprovalText(String received, String approved)
  {
    return getAcceptApprovalText(received, approved, SystemUtils.isWindowsEnvironment());
  }
  public static String getAcceptApprovalText(String received, String approved, boolean windowsEnviroment)
  {
    if (windowsEnviroment)
    {
      return String.format("move /Y \"%s\"  \"%s\"", received, approved);
    }
    else
    {
      return String.format("mv -f %s %s", formatFilePathForCommandLine(received),
          formatFilePathForCommandLine(approved));
    }
  }
  private static String formatFilePathForCommandLine(String filePath)
  {
    if (filePath.contains(" "))
    { return '"' + filePath + '"'; }
    return filePath;
  }
}
