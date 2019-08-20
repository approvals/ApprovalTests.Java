package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;

import com.spun.util.ObjectUtils;

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
