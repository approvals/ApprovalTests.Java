package com.spun.util.parser.tests;

import junit.framework.TestCase;

import com.spun.util.parser.TemplateStringUtils;

public class TemplateStringUtilsTest
  extends TestCase 
{
  public static UseCase useCases[] = {new UseCase("Hello Jack", 20, " ", "Hello Jack          ")};
	/***********************************************************************/
  public void testTextPadding()
  {
    for (int i = 0; i < useCases.length; i++)
    {
      UseCase useCase = useCases[i];
      assertEquals("percentUseCases[" + i + "]" , useCase.result, TemplateStringUtils.pad(useCase.string, useCase.length, useCase.padWith));
    }
  }
  /***********************************************************************/
	public static class UseCase
	{
	  public String padWith;
    public String string;
    public int length;
    public String result;
    /***********************************************************************/
    public UseCase(String string, int length, String padWith, String result)
    {
      super();
      this.padWith = padWith;
      this.string = string;
      this.length = length;
      this.result = result;
    }
	}
  /***********************************************************************/
  /***********************************************************************/
}
