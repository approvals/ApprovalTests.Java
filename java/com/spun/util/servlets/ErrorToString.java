package com.spun.util.servlets;

import com.spun.util.MySystem;
import com.spun.util.parser.TemplateError;

public class ErrorToString implements SecondaryErrorProcessor
{
  @Override
  public String processError(TemplateError original, Throwable secondary)
  {
    MySystem.warning(secondary);
    TemplateError e = new TemplateError(secondary, this);
    String output = "<PRE>" + original.getMessage() + "\n" + original.getStackTrace() + "</PRE>" + "<BR><PRE>"
        + e.getMessage() + "\n" + e.getStackTrace() + "</PRE>";
    return output;
  }
}
