package org.teachingkidsprogramming.recipes.quizzes.tests;

import junit.framework.TestCase;

import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.Turtle;
import org.teachingextensions.logo.utils.TortoiseUtils;
import org.teachingextensions.windows.MessageBox;
import org.teachingkidsprogramming.recipes.quizzes.graders.HiLowQuiz;
import org.teachingkidsprogramming.recipes.quizzes.graders.HiLowQuizGrader;

@UseReporter({DelayedClipboardReporter.class, DiffReporter.class})
public class HiLowQuizTest extends TestCase
{
  public static class HiLowCorrectQuiz extends HiLowQuiz
  {
    @Override
    public void question1()
    {
      //        if the Y position of the tortoise is 115
      if (Tortoise.getY() == 115)
      {
        //         turn the tortoise to the right 63 degrees 
        Tortoise.turn(63);
      }
    }
    @Override
    public void question2()
    {
      //        if the X position of tortoise is less than Y position of tortoise
      if (Tortoise.getX() < Tortoise.getY())
      {
        //         turn the tortoise 54 degrees to the left
        Tortoise.turn(-54);
      }
      else
      {
        //        otherwise turn the tortoise 22 degrees to the right
        Tortoise.turn(22);
      }
    }
    @Override
    public void question3()
    {
      //        display the message "elcomeway omehay!"
      MessageBox.showMessage("elcomeway omehay!");
    }
    @Override
    public void question4()
    {
      //    if the Y position of tortoise is greater than 50
      if (Tortoise.getY() > 50)
      {
        //         turn the tortoise 177 degrees to the left
        Tortoise.turn(-177);
      }
    }
  }
  public void testCorrect() throws Exception
  {
    HiLowQuizGrader.TURTLE_SPEED = Turtle.TEST_SPEED;
    new HiLowQuizGrader().grade(new HiLowCorrectQuiz());
    TortoiseUtils.verifyForOs();
  }
  public static class HiLowIncorrectQuiz extends HiLowQuiz
  {
    @Override
    public void question1()
    {
    }
    @Override
    public void question2()
    {
    }
    @Override
    public void question3()
    {
    }
    @Override
    public void question4()
    {
    }
  }
  public void testIncorrect() throws Exception
  {
    HiLowQuizGrader.TURTLE_SPEED = Turtle.TEST_SPEED;
    new HiLowQuizGrader().grade(new HiLowIncorrectQuiz());
    TortoiseUtils.verifyForOs();
  }
}
