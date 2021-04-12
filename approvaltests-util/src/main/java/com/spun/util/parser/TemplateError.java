package com.spun.util.parser;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.velocity.context.Context;
import com.spun.util.velocity.ContextAware;

public class TemplateError implements ContextAware
{
  private String stackTrace = null;
  private String className  = null;
  private String message    = null;
  private String cause;
  public TemplateError(Throwable t)
  {
    this(t, null);
  }
  public TemplateError(Throwable t, java.lang.Object o)
  {
    if (o != null)
    {
      className = o.getClass().getName();
    }
    cause = getCause(t);
    message = t.getMessage();
    if (message != null && message.startsWith(cause))
    {
      message = message.substring(cause.length() + 1);
    }
    StringWriter output = new StringWriter();
    PrintWriter pw = new PrintWriter(output);
    t.printStackTrace(pw);
    pw.flush();
    stackTrace = output.toString();
  }
  private String getCause(Throwable t)
  {
    if (t.getCause() == null)
    {
      return t.getClass().getName();
    }
    else
    {
      return getCause(t.getCause());
    }
  }
  public String getMessage()
  {
    return message;
  }
  public String getClassName()
  {
    return className;
  }
  public String getStackTrace()
  {
    return stackTrace;
  }
  public String getErrorInfo()
  {
    return message + "\n" + stackTrace;
  }
  public String getCause()
  {
    return cause;
  }
  public void setupContext(Context context)
  {
    context.put("error", this);
  }
}