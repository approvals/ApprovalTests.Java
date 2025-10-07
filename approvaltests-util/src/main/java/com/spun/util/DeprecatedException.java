package com.spun.util;

import com.spun.util.introspection.Caller;
import com.spun.util.logger.SimpleLogger;

public class DeprecatedException extends FormattedException
{
  private static final long serialVersionUID = 1L;
  public DeprecatedException(String useNewMethod, Object... formattingParams)
  {
    super("%s is Depercated.\n Instead, please use :\n %s", methodName(Caller.get(1)),
        String.format(useNewMethod, formattingParams));
    SimpleLogger.message(getMessage());
  }

  private static Object methodName(StackTraceElement trace)
  {
    if (trace.getMethodName().equals("<init>"))
    { return trace.getClassName(); }
    return trace.getMethodName();
  }
}