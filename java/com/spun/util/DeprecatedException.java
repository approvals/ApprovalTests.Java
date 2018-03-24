package com.spun.util;

import com.spun.util.stacktrace.Caller;

public class DeprecatedException extends FormattedException
{
  public DeprecatedException(String useNewMethod, Object... formattingParams)
  {
    super("%s is Depercated.\n Instead, please use :\n %s", Caller.get(1).getMethodName(),
        String.format(useNewMethod, formattingParams));
  }
}
