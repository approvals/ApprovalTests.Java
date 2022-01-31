package com.spun.util.parser;

import com.spun.util.NumberUtils;

import java.text.NumberFormat;
import java.util.Locale;

public class TemplateDouble
{
  protected double            amount                  = 0;
  private static NumberFormat decimalFormat           = null;
  protected String            defaultPrefix           = null;
  protected String            defaultPostfix          = null;
  protected int               defaultMinimumPrecision = 2;
  protected int               defaultMaximumPrecision = 2;
  static
  {
    decimalFormat = NumberFormat.getNumberInstance(Locale.US);
    decimalFormat.setMaximumFractionDigits(2);
    decimalFormat.setMinimumFractionDigits(2);
  }
  public TemplateDouble(double amount)
  {
    this.amount = amount;
  }
  public TemplateDouble(double amount, String defaultPrefix, String defaultPostfix, int defaultMinimumPrecision,
      int defaultMaximumPrecision)
  {
    this.amount = amount;
    this.defaultPostfix = defaultPostfix;
    this.defaultPrefix = defaultPrefix;
    this.defaultMinimumPrecision = defaultMinimumPrecision;
    this.defaultMaximumPrecision = defaultMaximumPrecision;
  }
  public TemplateDouble(Number amount)
  {
    this(amount.doubleValue());
  }
  public String inDollarFormat()
  {
    return inFormat(amount, "$", 2, 2, "", true);
  }
  public CurrencyAmount asCurrency()
  {
    return new CurrencyAmount(amount);
  }
  public String asInteger()
  {
    return inFormat(amount, null, 0, 0, null, true);
  }
  public String inFormat(String prefix, int precision, int minimumPrecision, String postfix, boolean useGrouping)
  {
    return inFormat(amount, prefix, precision, minimumPrecision, postfix, useGrouping);
  }
  public static String inFormat(double amount, String prefix, int maximumPrecision, int minimumPrecision,
      String postfix, boolean useGrouping)
  {
    String value = "";
    NumberFormat dFormat = decimalFormat;
    if ((maximumPrecision != 2) || (minimumPrecision != 2) || !useGrouping)
    {
      dFormat.setMaximumFractionDigits(maximumPrecision);
      dFormat.setMinimumFractionDigits(minimumPrecision);
      dFormat.setGroupingUsed(useGrouping);
    }
    value += dFormat.format(amount);
    if (prefix != null)
    {
      if (value.charAt(0) != '-')
      {
        value = prefix + value;
      }
      else
      {
        value = '-' + prefix + value.substring(1);
      }
    }
    value += (postfix != null) ? postfix : "";
    return value;
  }
  protected int getDefaultMinimumPrecision(int maximumPrecision)
  {
    return maximumPrecision < defaultMinimumPrecision ? maximumPrecision : defaultMinimumPrecision;
  }
  public String asInt()
  {
    return inFormat(null, 0, 0, null, false);
  }
  public String asDouble()
  {
    return inFormat(null, 2, 2, null, false);
  }
  public String asNegative()
  {
    return inFormat(-amount, defaultPrefix, 2, 2, defaultPostfix, false);
  }
  public TemplateDouble getAbsoluteValue()
  {
    return new TemplateDouble(-amount, defaultPrefix, defaultPostfix, defaultMinimumPrecision,
        defaultMaximumPrecision);
  }
  public boolean isZero()
  {
    return NumberUtils.equals(0, this.amount, 0.0005);
  }
  public TemplateDouble divide(int by)
  {
    return new TemplateDouble(amount / by);
  }
  public String toString()
  {
    return inFormat(defaultPrefix, defaultMaximumPrecision, getDefaultMinimumPrecision(defaultMaximumPrecision),
        defaultPostfix, true);
  }
  public double getAmount()
  {
    return amount;
  }
}
