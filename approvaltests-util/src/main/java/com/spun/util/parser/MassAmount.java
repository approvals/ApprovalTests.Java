package com.spun.util.parser;

import com.spun.util.StringUtils;

public class MassAmount extends TemplateDouble
{
  public static final int     GRAMS              = 0;
  public static final int     KILOGRAMS          = 1;
  public static final int     POUNDS             = 2;
  public static final String  UNITS[]            = {"Grams", "Kilograms", "Pounds"};
  private static final double UNIT_CONVERSIONS[] = {1, .001, 0.00220467};

  public MassAmount(double amount)
  {
    super(amount, "", "", 0, 2);
  }

  public MassAmount(double amount, int units)
  {
    super(convertUnits(amount, units, GRAMS), "", "", 0, 2);
  }

  public static int getUnits(String value)
  {
    return StringUtils.resolveEnumeration(value, UNITS);
  }

  public static double convertUnits(double amount, int fromUnits, int toUnits)
  {
    return (amount / UNIT_CONVERSIONS[fromUnits]) * UNIT_CONVERSIONS[toUnits];
  }

  public double convertUnits(int toUnits)
  {
    return this.amount * UNIT_CONVERSIONS[toUnits];
  }

  public double getAmountInUnits(int desiredUnits)
  {
    return convertUnits(desiredUnits);
  }

  public String inUnits(String desiredUnits)
  {
    return inFormat(getAmountInUnits(getUnits(desiredUnits)), defaultPrefix, defaultMaximumPrecision, defaultMinimumPrecision, defaultPostfix, true);
  }

  public String inUnits(String desiredUnits, int maximumPrecision, int minimumPrecision)
  {
    return inFormat(getAmountInUnits(getUnits(desiredUnits)), defaultPrefix, maximumPrecision, getDefaultMinimumPrecision(maximumPrecision),
        defaultPostfix, true);
  }

  public int getPoundsRoundedUp()
  {
    return getRoundedUp(MassAmount.POUNDS);
  }

  public int getRoundedUp(int inUnits)
  {
    return (int) Math.ceil(convertUnits(inUnits) - 0.001);
  }


}
