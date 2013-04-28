package org.teachingkidsprogramming.recipes.homework.tests;

import junit.framework.TestCase;

import org.teachingkidsprogramming.recipes.homework.Homework03Ifs;

public class Homework03Test extends TestCase
{
  public void testQuestions() throws Exception
  {
    assertQuestion("doesABear", "woods");
    assertQuestion("neverEverEver", "chocolate");
    assertQuestion("isThePopeCatholic", true);
    assertQuestion("trueOrFalse", true);
    assertQuestion("letSleepingBabiesLie", false);
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
      Homework03Ifs h = new Homework03Ifs();
      if (answer instanceof Integer)
      {
        h.____ = (Integer) answer;
      }
      if (answer instanceof String)
      {
        h.___ = (String) answer;
      }
      if (answer instanceof Boolean)
      {
        h._____ = (Boolean) answer;
        h.______ = (Boolean) answer;
      }
      Homework03Ifs.class.getMethod(methodName).invoke(h);
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
      Homework03Ifs h = new Homework03Ifs();
      Homework03Ifs.class.getMethod(methodName).invoke(h);
    }
    catch (Throwable e)
    {
      failed = true;
    }
    assertTrue("the method " + methodName + " is already passing", failed);
  }
}
