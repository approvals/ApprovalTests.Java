package org.approvaltests.reporters;

import com.spun.util.ObjectUtils;
import org.approvaltests.core.ApprovalFailureReporter;

public class NotePadLancher implements ApprovalFailureReporter
{
  @Override
  public void report(String received, String approved)
  {
    {
      String text = "\"C:\\Program Files\\Notepad++\\notepad++.exe\" \"%s\"";
      String text2 = String.format(text, received);
      ObjectUtils.throwAsError(() -> Runtime.getRuntime().exec(text2));
    }
  }
}
