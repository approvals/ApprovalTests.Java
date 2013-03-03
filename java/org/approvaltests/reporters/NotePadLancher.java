package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;

public class NotePadLancher implements ApprovalFailureReporter
{
  @Override
  public void report(String received, String approved) throws Exception
  {
    {
      String text = "\"C:\\Program Files\\Notepad++\\notepad++.exe\" \"%s\"";
      text = String.format(text, received);
      Runtime.getRuntime().exec(text);
    }
  }
}
