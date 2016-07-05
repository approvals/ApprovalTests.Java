package com.spun.util;

import com.spun.util.logger.SimpleLogger;

public class FormattedException extends RuntimeException
{
  public FormattedException(String string, Object... params)
  {
    super(String.format(string, params));
    SimpleLogger.variable(this.getMessage());
  }
}
