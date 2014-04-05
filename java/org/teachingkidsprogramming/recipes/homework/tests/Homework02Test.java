package org.teachingkidsprogramming.recipes.homework.tests;

import junit.framework.TestCase;

import org.teachingkidsprogramming.recipes.deepdives.Homework02;

public class Homework02Test extends TestCase
{
  public void testQuestions() throws Exception
  {
    assertQuestion("youCanReadVariables", 5);
    assertQuestion("youCanSaveVariables", 10);
    assertCurrentlyFailing("youCanDoMathWithVariables");
    assertQuestion("youCanChangeVariables", 10);
    assertQuestion("variablesAreSnotStuck", 0);
    assertQuestion("youCanAddToAVariable", 1);
    assertQuestion("youCanAddInMultipleWays", 1);
    assertQuestion("youCanAddOneInOneMoreWay", 4);
    assertQuestion("youCanSubtractFromAVariable", 3);
    assertQuestion("youCanSubtractOneInOneMoreWay", 98);
    assertQuestion("youCanMultiplyVariables", 10);
    assertQuestion("youCanDivideVariables", 36);
    assertQuestion("variablesOnlyExistWithinTheMethod", "bike");
    assertQuestion("methodsCanReturnValues", "gum");
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
      Homework02 h = new Homework02();
      if (answer instanceof Integer)
      {
        h.____ = (Integer) answer;
      }
      if (answer instanceof String)
      {
        h.___ = (String) answer;
      }
      Homework02.class.getMethod(methodName).invoke(h);
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
      Homework02 h = new Homework02();
      Homework02.class.getMethod(methodName).invoke(h);
    }
    catch (Throwable e)
    {
      failed = true;
    }
    assertTrue("the method " + methodName + " is already passing", failed);
  }
}
