package org.teachingkidsprogramming.recipes.quizzes.tests;

import junit.framework.TestCase;

import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.FileLauncherReporter;
import org.approvaltests.reporters.UseReporter;
import org.teachingextensions.logo.ColorWheel;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.Turtle;
import org.teachingextensions.logo.utils.TortoiseUtils;
import org.teachingkidsprogramming.recipes.quizzes.graders.PentagonCrazyQuizGrader;
import org.teachingkidsprogramming.recipes.quizzes.graders.PentagonQuizAdapter;

@UseReporter({DelayedClipboardReporter.class, FileLauncherReporter.class})
public class PentagonCrazyQuizTest extends TestCase
{
  public static class PentagonCrazyCorrectQuiz extends PentagonQuizAdapter
  {
    //      Create a method called thread
    public void question1()
    {
      thread();
    }
    public void thread()
    {
      //       that moves the tortoise 6 pixels
      Tortoise.move(6);
    }
    @Override
    public void question2()
    {
      //        Do the following 76 times
      for (int i = 0; i < 76; i++)
      {
        //        Quiz.Stitch()
        stitch();
      }
    }
    @Override
    public void question3()
    {
      //        Add lime to the color wheel
      ColorWheel.addColor(Colors.Greens.Lime);
    }
    @Override
    public void question4()
    {
      //        Add red to the color wheel
      ColorWheel.addColor(Colors.Reds.Red);
    }
  }
  public void testCorrect() throws Exception
  {
    PentagonCrazyQuizGrader.TURTLE_SPEED = Turtle.TEST_SPEED;
    new PentagonCrazyQuizGrader().grade(new PentagonCrazyCorrectQuiz());
    TortoiseUtils.verifyForOs();
  }
  public static class PentagonCrazyIncorrectQuiz extends PentagonQuizAdapter
  {
    public void question1()
    {
      thread();
    }
    public void thread()
    {
      Tortoise.move(2);
    }
    public void question2()
    {
      for (int i = 0; i < 50; i++)
      {
        stitch();
      }
    }
    public void question3()
    {
      ColorWheel.addColor(Colors.Greens.LimeGreen);
    }
    public void question4()
    {
      ColorWheel.addColor(Colors.Reds.Tomato);
    }
  }
  public void testIncorrect() throws Exception
  {
    PentagonCrazyQuizGrader.TURTLE_SPEED = Turtle.TEST_SPEED;
    new PentagonCrazyQuizGrader().grade(new PentagonCrazyIncorrectQuiz());
    TortoiseUtils.verifyForOs();
  }
}
