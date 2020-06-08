package com.spun.util.parser.tests;

import junit.framework.TestCase;
import com.spun.util.parser.PercentageAmount;

public class TemplateDoubleTest extends TestCase
{
  public static UseCase percentUseCases[] = {new UseCase(.10457, "10.46%")};
  
  public void testPercentageAmount()
  {
    for (int i = 0; i < percentUseCases.length; i++)
    {
      assertEquals("percentUseCases[" + i + "]", percentUseCases[i].result, new PercentageAmount(percentUseCases[i].amount).toString());
    }
  }
  
  public static class UseCase
  {
    public String result;
    public double amount;
    
    public UseCase(double amount, String result)
    {
      super();
      this.amount = amount;
      this.result = result;
    }
  }
}
