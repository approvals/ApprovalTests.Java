package org.approvaltests.reporters;

import com.spun.util.ObjectUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.core.ApprovalFailureReporter;

public class NotePadLauncher implements ApprovalFailureReporter
{
  @Override
  public boolean report(String received, String approved)
  {
    {
      String text = "\"C:\\Program Files\\Notepad++\\notepad++.exe\" \"%s\"";
      String text2 = String.format(text, received);
      try
      {
        Runtime.getRuntime().exec(text2);
        return true;
      }
      catch (Exception e)
      {
        SimpleLogger.warning("Error launching Notepad++.", e);
        return false;
      }
    }
  }
}
