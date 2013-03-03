package org.approvaltests.tests;

public class MyStringBuilder
{

  private StringBuffer builder = new StringBuffer();

  public void append(String string)
  {
    builder.append(string);
  }
  @Override
  public String toString()
  {
    return builder.toString();
  }
}
