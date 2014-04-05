package org.teachingkidsprogramming.recipes.quizzes.graders;

import java.lang.reflect.Method;

public class HousesQuizAdapter
{
  public int length = 0;
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
    call(this, "small");
  }
  public void question2()
  {
    call(this, "medium");
  }
  public void question3()
  {
    call(this, "large");
  }
  public void question4()
  {
    call(this, "moveTheLength");
  }
  public void question5()
  {
    call(this, "turnTheCorner");
  }
  public void question6()
  {
    call(this, "drawASide");
  }
}
