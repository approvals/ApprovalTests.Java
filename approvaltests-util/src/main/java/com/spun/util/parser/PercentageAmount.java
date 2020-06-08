package com.spun.util.parser;

import com.spun.util.NumberUtils;

public class PercentageAmount extends TemplateDouble
{
  /************************************************************************/
  public PercentageAmount(double amount)
  {
    super(amount, "", "%", 0, 2);
  }
  /************************************************************************/
  public static PercentageAmount createFromRatio(double a, double b)
  {
    return new PercentageAmount(getAmount(a, a + b));
  }
  /************************************************************************/
  public static PercentageAmount createFromFraction(double a, double b)
  {
    return new PercentageAmount(getAmount(a, b));
  }
  /************************************************************************/
  private static double getAmount(double a, double b)
  {
    double amount = NumberUtils.equals(b, 0, 0.00001) ? 1 : (a / b);
    return amount;
  }
  /************************************************************************/
  public PercentageAmount getInverse()
  {
    return new PercentageAmount(1 - amount);
  }

  public String inDefaultFormat()
  {
    return inFormat(amount * 100, "", 2, 0, "%", true);
  }

  public String inIntegerFormat()
  {
    return inFormat(amount * 100, "", 0, 0, "%", true);
  }

  public String inDefaultFormat(String prefix, int precision, int minimumPrecision, String postfix,
      boolean useGrouping)
  {
    return inFormat(amount * 100, prefix, precision, minimumPrecision, postfix, useGrouping);
  }
  /************************************************************************/
  public String toString()
  {
    return inDefaultFormat();
  }
  /************************************************************************/
  public String withoutPostfix()
  {
    return inFormat(amount * 100, null, 6, 0, null, false);
  }
  /************************************************************************/
  /************************************************************************/
}
