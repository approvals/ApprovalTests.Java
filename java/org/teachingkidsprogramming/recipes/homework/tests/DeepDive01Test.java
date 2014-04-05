package org.teachingkidsprogramming.recipes.homework.tests;

import junit.framework.TestCase;

import org.teachingkidsprogramming.recipes.deepdives.DeepDive01ForLoops;

public class DeepDive01Test extends TestCase
{
  public void testQuestions() throws Exception
  {
    assertQuestion("numbersDoNotNeedQuotes", 42);
    assertQuestion("defaultWidthForTheTortoise", 2);
    assertQuestion("stringsNeedQuotes", "Green");
    assertQuestion("stringsCanIncludeSpaces", "This is a string");
    assertQuestion("changingThePenWidthTo5", 5);
    assertQuestion("movingTheTortoise100Pixels", 100);
    assertQuestion("theTortoiseTurns21", 21);
    assertQuestion("theTortoiseTurns15Twice", 15);
    assertQuestion("howFastCanTheTortoiseGo", 10);
    assertQuestion("assigningVariables", 101);
    assertQuestion("combiningNumbers", 7);
    assertQuestion("combiningText", "Peter Pan");
    assertQuestion("combiningTextAndNumbers", "Henry The 8");
    assertQuestion("textIsTextEvenWhenItsNumbers", "34");
    assertQuestion("combiningTextInALoop", "AHHH");
    assertQuestion("forLoopsEndAtTheEnd", 5);
    assertQuestion("forLoopsCanStartAnywhere", 7);
    assertQuestion("forLoopsCanSkip", 2);
    assertQuestion("forLoopsCanSkipUpAndDown", -3);
    assertQuestion("forLoopsCanGoBackwards", -1);
    assertQuestion("semicolonsMessUpForLoops", "AH");
  }
  public void assertQuestion(String methodName, Object answer) throws Exception
  {
    assertCurrentlyFailing(methodName);
    assertCorrectAnswer(answer, methodName);
  }
  public void assertCorrectAnswer(Object answer, String methodName) throws Exception
  {
    try
    {
      DeepDive01ForLoops h = new DeepDive01ForLoops();
      h.init();
      if (answer instanceof Integer)
      {
        h.____ = (Integer) answer;
      }
      if (answer instanceof String)
      {
        h.___ = (String) answer;
      }
      DeepDive01ForLoops.class.getMethod(methodName).invoke(h);
    }
    catch (Exception e)
    {
      if (e.getCause() != null) { throw (Error) e.getCause(); }
      throw e;
    }
  }
  public void assertCurrentlyFailing(String methodName)
  {
    boolean failed = false;
    try
    {
      DeepDive01ForLoops h = new DeepDive01ForLoops();
      h.init();
      DeepDive01ForLoops.class.getMethod(methodName).invoke(h);
    }
    catch (Throwable e)
    {
      failed = true;
    }
    assertTrue("the method " + methodName + " is already passing", failed);
  }
}
