package com.spun.util.parser;

public class CurrencyAmount extends TemplateDouble
{
  public CurrencyAmount(double amount)
  {
    super(amount, "$", "", 2, 2);
  }

  public String toString()
  {
    return inFormat("$", 2, 2, null, true);
  }

  public static String inDefaultFormat(double d)
  {
    return inFormat(d, "$", 2, 2, null, true);
  }

  public String withoutCents()
  {
    return inFormat("$", 0, 0, null, true);
  }
}
