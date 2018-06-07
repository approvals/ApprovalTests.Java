package org.approvaltests.strings;

/**
 * @deprecated use lambdas: {@code  a  -> from + " => " + to + "\n" }
 */
@Deprecated
public class Printer<T>
{
  public static String multiline(String from, String to)
  {
    return (from + "\n => \n" + to + "\n" + "------------------------------------");
  }
  public static String printLine(Object from, Object to)
  {
    return from + " => " + to + "\n";
  }
}
