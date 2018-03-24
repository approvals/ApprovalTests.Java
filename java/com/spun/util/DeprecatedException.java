package com.spun.util;

import com.spun.util.stacktrace.Caller;

public class DeprecatedException extends FormattedException
{
  public DeprecatedException(String useNewMethod, Object... formattingParams)
  {
    super("%s is Depercated.\n Instead, please use :\n %s", methodName(Caller.get(1)),
        String.format(useNewMethod, formattingParams));
  }
  private static Object methodName(StackTraceElement trace)
  {
    if (trace.getMethodName().equals("<init>")) { return trace.getClassName(); }
    return trace.getMethodName();
  }
}
