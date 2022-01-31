package com.spun.util.parser;

import com.spun.util.NumberUtils;
import org.lambda.query.Query;

public class TemplateNumberUtils
{
  public static TemplateNumberUtils INSTANCE = new TemplateNumberUtils();
  private TemplateNumberUtils()
  {
  }
  public static boolean isZero(double d)
  {
    return isZero(d, 0.005);
  }
  public static boolean isZero(Number d)
  {
    return d == null || isZero(d.doubleValue(), 0.005);
  }
  public static int asInt(Number d)
  {
    return d == null ? 0 : d.intValue();
  }
  public static boolean isZero(double d, double delta)
  {
    return NumberUtils.equals(0, d, delta);
  }
  private int getScaling(double have, double max, double scale)
  {
    if (max == 0)
    {
      throw new Error("Maximum cannot be zero");
    }
    else
    {
      return (int) ((have * scale) / max);
    }
  }
  public int getScaling(Integer have, Integer max, Integer scale)
  {
    return getScaling(have.doubleValue(), max.doubleValue(), scale.doubleValue());
  }
  public static double doArithmetic(int arg1, String operation, int arg2)
  {
    return doArithmetic(new String[]{"" + arg1, operation, "" + arg2});
  }
  public static double doArithmetic(String[] params)
  {
    if (params == null || params.length == 0)
    { return 0.00; }
    double totalNumber = 0;
    double currentNumber = 0;
    char operator = '+';
    for (int i = 0; i < params.length; i++)
    {
      if (i % 2 == 0)
      {
        currentNumber = NumberUtils.load(params[i], 0.00);
        switch (operator)
        {
          case '+' :
            totalNumber = totalNumber + currentNumber;
            break;
          case '-' :
            totalNumber = totalNumber - currentNumber;
            break;
          case '*' :
            totalNumber = totalNumber * currentNumber;
            break;
          case '/' :
            totalNumber = totalNumber / currentNumber;
            break;
          case '%' :
            totalNumber = totalNumber % currentNumber;
            break;
          default :
            throw new Error("Unknown arithmetic operator " + operator);
        }
      }
      else
      {
        operator = params[i].charAt(0);
      }
    }
    return totalNumber;
  }
  public static TemplateDouble max(Number... numbers)
  {
    return new TemplateDouble(Query.max(numbers));
  }
  public static TemplateDouble max(Number n1, Number n2, Number n3)
  {
    return max(new Number[]{n1, n2, n3});
  }
}
