package org.teachingkidsprogramming.recipes.quizzes.graders;

import java.lang.reflect.Method;

public class HousesQuizAdapter
{
  public int length = 0;
  private void call(String methodName)
  {
    try
    {
      Method method = this.getClass().getDeclaredMethod(methodName);
      method.setAccessible(true);
      method.invoke(this);
    }
    catch (Throwable e)
    {
      // ignore this
    }
  }
  public void question1()
  {
    call("small");
  }
  public void question2()
  {
    call("medium");
  }
  public void question3()
  {
    call("large");
  }
  public void question4()
  {
    call("moveTheLength");
  }
  public void question5()
  {
    call("turnTheCorner");
  }
  public void question6()
  {
    call("drawASide");
  }
}
