package com.spun.util.tests;

import com.spun.util.WhiteSpaceStripper;

import junit.framework.TestCase;

public class WhiteSpaceStripperTest extends TestCase
{
  private static final int STRIP_BLANK_LINES = 0;
  UseCase[]                useCases          = {new UseCase(STRIP_BLANK_LINES, "  hello \n    \n   \n",
      "  hello "),
                                                new UseCase(STRIP_BLANK_LINES, "  hello \r\n    \n a \n",
                                                    "  hello \n a "),
                                                new UseCase(STRIP_BLANK_LINES, "  hello  ", "  hello  ")};

  public void test()
  {
    for (int i = 0; i < useCases.length; i++)
    {
      testUseCase(useCases[i], i);
    }
  }

  private void testUseCase(UseCase useCase, int i)
  {
    switch (useCase.functionCall)
    {
      case STRIP_BLANK_LINES :
        assertEquals("Stripped For [" + i + "]", useCase.expectedString,
            WhiteSpaceStripper.stripBlankLines(useCase.startingString));
        break;
    }
  }

  public static void main(String[] args)
  {
    junit.textui.TestRunner.run(WhiteSpaceStripperTest.class);
  }

  public static class UseCase
  {
    String startingString;
    String expectedString;
    int    functionCall;
    public UseCase(int functionCall, String startingString, String expectedString)
    {
      this.startingString = startingString;
      this.functionCall = functionCall;
      this.expectedString = expectedString;
    }
  }


}
