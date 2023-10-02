package org.approvaltests.reporters;

import com.spun.util.StringUtils;
import com.spun.util.WindowUtils;
import org.approvaltests.core.ApprovalFailureReporter;

import java.awt.*;

/**
 * A getReporter which creates the command to accept the received
 * file as the approve file and copies it to the clipboard:<br>
 * <code>move received.txt approved.txt</code>
 */
public class DelayedClipboardReporter implements ApprovalFailureReporter
{
  private static StringBuffer text = new StringBuffer();
  @Override
  public boolean report(String received, String approved)
  {
    if (!isWorkingInThisEnvironment(received))
    { return false; }
    String commandLine = ClipboardReporter.getCommandLine(received, approved);
    text.append(commandLine + StringUtils.NEW_LINE);
    WindowUtils.copyToClipBoard(text.toString(), false);
    return true;
  }
  /**
   * The clipboard will not be available in a headless environment.
   */
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    return !GraphicsEnvironment.isHeadless();
  }
}
