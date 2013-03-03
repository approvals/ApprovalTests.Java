package com.spun.util.velocity;

import org.apache.velocity.util.introspection.Info;

public class VelocityParsingError extends Error
{

  private String message;
  private transient Info info;

  public VelocityParsingError(String message, Info info)
  {
    this.message = message;
    this.info = info;
  }
  
  public String getMessage()
  {
    return message + "  " + getInfoText(info);
  }
  /***********************************************************************/
  public static String getInfoText(Info i)
  {
    return " at [" + i.getLine() + "," + i.getColumn() + "]" + " in template " + i.getTemplateName();
  }

  public Info getInfo()
  {
    return info;
  }

}
