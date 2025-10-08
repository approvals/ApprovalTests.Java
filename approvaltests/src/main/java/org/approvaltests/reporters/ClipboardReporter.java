package org.approvaltests.reporters;

import com.spun.util.SystemUtils;
import com.spun.util.WindowUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.core.ApprovalFailureReporter;

import java.awt.*;
import java.io.File;

/**
 * A getReporter which creates the command to accept the received
 * file as the approve file and copies it to the clipboard:<br>
 * <code>move received.txt approved.txt</code>
 */
public class ClipboardReporter implements ApprovalFailureReporter
{
  @Override
  public boolean report(String received, String approved)
  {
    if (GraphicsEnvironment.isHeadless())
    { return false; }
    WindowUtils.copyToClipBoard(getCommandLine(received, approved), false);
    return true;
  }

  public static String getCommandLine(String received, String approved)
  {
    File r = new File(received);
    File a = new File(approved);
    return ClipboardReporter.getAcceptApprovalText(r.getAbsolutePath(), a.getAbsolutePath());
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
