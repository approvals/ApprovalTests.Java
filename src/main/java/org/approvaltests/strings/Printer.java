package org.approvaltests.strings;

import org.lambda.functions.implementations.F1;

public class Printer<T> extends F1<T, String>
{
  public Printer(T a, Object... extraVariables)
  {
    super(a, extraVariables);
  }
  public void multiline(String from, String to)
  {
    ret(from + "\r\n => \r\n" + to + "\r\n" + "------------------------------------");
  }
  public static String printLine(Object from, Object to)
  {
    return from + " => " + to + "\r\n";
  }
  public void print(Object from, Object to)
  {
    ret(from + " => " + to);
  }
  public void format(String format, Object... params)
  {
    ret(String.format(format, params));
  }
}
