package com.spun.util.servlets;

public class ServletParameterException extends RuntimeException
{

  private String value;
  private String label;

  public ServletParameterException(String label, String value)
  {
    this.label = label;
    this.value = value;
  }

  public String getMessage()
  {
    
    return "[" + label + " = '" + value + "']";
  }
  
}
