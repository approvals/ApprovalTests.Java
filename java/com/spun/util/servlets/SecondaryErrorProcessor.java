package com.spun.util.servlets;

import com.spun.util.parser.TemplateError;

public interface SecondaryErrorProcessor
{
  public String processError(TemplateError error, Throwable t2);
}
