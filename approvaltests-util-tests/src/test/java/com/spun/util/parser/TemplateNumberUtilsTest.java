package com.spun.util.parser;

import com.spun.util.parser.TemplateNumberUtils;

import junit.framework.TestCase;

public class TemplateNumberUtilsTest extends TestCase
{
  public static UseCase useCases[] = {new UseCase(new String[]{"3", "+", "5"}, 8),
                                      new UseCase(new String[]{"3", "-", "5"}, -2),
                                      new UseCase(new String[]{"3", "*", "5"}, 15),
                                      new UseCase(new String[]{"15", "/", "3"}, 5),
                                      new UseCase(new String[]{"9", "+", "3"}, 12),
                                      new UseCase(new String[]{"1", "+", "2", "*", "3", "-", "4", "/", "5"}, 1)};
  
  public void testDoArithmetic()
  {
    for (int i = 0; i < useCases.length; i++)
    {
      assertEquals("useCase[" + i + "]", useCases[i].result, TemplateNumberUtils.doArithmetic(useCases[i].params),
          0.005);
    }
  }
  public void testMax() throws Exception
  {
    Double numbers[] = {10.1, 8.0, 13.5, 5.0};
    assertEquals(13.5, TemplateNumberUtils.max((Number[]) numbers).getAmount());
  }
  
  public static class UseCase
  {
    public double   result;
    public String[] params;
    
    public UseCase(String[] params, double result)
    {
      super();
      this.result = result;
      this.params = params;
    }
  }
}
