package org.approvaltests.namer;

import org.lambda.functions.Function0;

import com.spun.util.ThreadUtils;

public class IdeLabeller implements Function0<String>
{
  @Override
  public String call()
  {
    return getIde(ThreadUtils.getStackTrace());
  }
  private String getIde(StackTraceElement[] stackTrace)
  {
    for (StackTraceElement stackTraceElement : stackTrace)
    {
      String className = stackTraceElement.getClassName();
      if (className.contains("intellij"))
      {
        return "intellij";
      }
      else if (className.contains("eclipse"))
      { return "eclipse"; }
    }
    return "unknown";
  }
}
