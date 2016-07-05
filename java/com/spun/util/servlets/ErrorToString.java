package com.spun.util.servlets;

import com.spun.util.logger.SimpleLogger;
import com.spun.util.parser.TemplateError;

public class ErrorToString implements SecondaryErrorProcessor
{
  @Override
  public String processError(TemplateError original, Throwable secondary)
  {
    SimpleLogger.warning(secondary);
    TemplateError e = new TemplateError(secondary, this);
    String output = "<PRE>" + original.getMessage() + "\n" + original.getStackTrace() + "</PRE>" + "<BR><PRE>"
        + e.getMessage() + "\n" + e.getStackTrace() + "</PRE>";
    return output;
  }
}
