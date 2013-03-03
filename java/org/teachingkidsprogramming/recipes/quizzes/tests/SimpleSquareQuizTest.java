package org.teachingkidsprogramming.recipes.quizzes.tests;

import junit.framework.TestCase;

import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.FileLauncherReporter;
import org.approvaltests.reporters.UseReporter;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.Turtle;
import org.teachingextensions.logo.utils.TortoiseUtils;
import org.teachingkidsprogramming.recipes.quizzes.graders.SimpleSquareQuizGrader;
import org.teachingkidsprogramming.recipes.quizzes.graders.SquareQuiz;

@UseReporter({ClipboardReporter.class, FileLauncherReporter.class})
public class SimpleSquareQuizTest extends TestCase
{
  public static class SimpleSquareCorrectQuiz implements SquareQuiz
  {
    public void question1()
    {
      //  Turn the tortoise 1/5 of 360 degrees to the right
      Tortoise.turn(360 / 5);
    }
    public void question2()
    {
      //  Move the tortoise 110 pixels
      Tortoise.move(110);
    }
    public void question3()
    {
      //  Change the color the tortoise draws to yellow
      Tortoise.setPenColor(Colors.Yellows.Yellow);
    }
    public void question4()
    {
      //  Change the width of the line the tortoise draws to 5 pixels
      Tortoise.setPenWidth(5);
    }
  }
  public void testCorrect() throws Exception
  {
    SimpleSquareQuizGrader.TURTLE_SPEED = Turtle.TEST_SPEED;
    new SimpleSquareQuizGrader().grade(new SimpleSquareCorrectQuiz());
    TortoiseUtils.verifyForOs();
  }
  public static class SimpleSquareIncorrectQuiz implements SquareQuiz
  {
    public void question1()
    {
      //  Turn the tortoise 1/5 of 360 degrees to the right
      Tortoise.turn(360.0 / 6);
    }
    public void question2()
    {
      //  Move the tortoise 110 pixels
      Tortoise.move(75);
    }
    public void question3()
    {
      //  Change the color the tortoise draws to yellow
      Tortoise.setPenColor(Colors.Yellows.Gold);
    }
    public void question4()
    {
      //  Change the width of the line the tortoise draws to 5 pixels
      Tortoise.setPenWidth(9);
    }
  }
  public void testIncorrect() throws Exception
  {
    SimpleSquareQuizGrader.TURTLE_SPEED = Turtle.TEST_SPEED;
    new SimpleSquareQuizGrader().grade(new SimpleSquareIncorrectQuiz());
    TortoiseUtils.verifyForOs();
  }
}
