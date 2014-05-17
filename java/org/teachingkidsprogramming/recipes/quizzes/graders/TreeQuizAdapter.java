package org.teachingkidsprogramming.recipes.quizzes.graders;

import java.lang.reflect.Method;

public class TreeQuizAdapter
{
  public int length = 0;
  public int turn   = 0;
  public static void call(Object that, String methodName)
  {
    try
    {
      Method method = that.getClass().getDeclaredMethod(methodName);
      method.setAccessible(true);
      method.invoke(that);
    }
    catch (Throwable e)
    {
      // ignore this
    }
  }
  public void question1()
  {
    call(this, "doubleLength");
  }
  public void question2()
  {
    call(this, "decreaseTurn");
  }
  public void question3()
  {
    call(this, "setNinety");
  }
  public void question4()
  {
    call(this, "moveTheLength");
  }
}
