package org.teachingkidsprogramming.recipes.quizzes.graders;

import java.lang.reflect.Method;

public class HousesQuizAdapter extends HousesQuiz
{
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
  @Override
  public void question1()
  {
    call("small");
  }
  @Override
  public void question2()
  {
    call("medium");
  }
  @Override
  public void question3()
  {
    call("large");
  }
  @Override
  public void question4()
  {
    call("moveTheLength");
  }
  @Override
  public void question5()
  {
    call("turnTheCorner");
  }
  @Override
  public void question6()
  {
    call("drawASide");
  }
}
