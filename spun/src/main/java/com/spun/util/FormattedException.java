package com.spun.util;

public class FormattedException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  public FormattedException(String string, Object... params)
  {
    super(String.format(string, params));
    //SimpleLogger.variable(this.getMessage());
  }
}
