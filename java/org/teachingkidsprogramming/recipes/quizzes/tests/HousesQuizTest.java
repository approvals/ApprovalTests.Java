package org.teachingkidsprogramming.recipes.quizzes.tests;

import junit.framework.TestCase;

import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.FileLauncherReporter;
import org.approvaltests.reporters.UseReporter;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.Turtle;
import org.teachingextensions.logo.utils.TortoiseUtils;
import org.teachingkidsprogramming.recipes.quizzes.graders.HousesQuizAdapter;
import org.teachingkidsprogramming.recipes.quizzes.graders.HousesQuizGrader;

@UseReporter({DelayedClipboardReporter.class, FileLauncherReporter.class})
public class HousesQuizTest extends TestCase
{
  public static class HousesCorrectQuiz extends HousesQuizAdapter
  {
    private void small()
    {
      //        'The current length is 7
      length = 7;
    }
    //      'Create a subroutine called Medium
    private void medium()
    {
      //      ' that sets the current length to 21
      length = 21;
    }
    //      'Create a subroutine called Large
    private void large()
    {
      //      that sets the current length to 63
      length = 63;
    }
    //      Create a subroutine called MoveTheLength
    private void moveTheLength()
    {
      //      ' that moves the Tortoise the current length
      Tortoise.move(length);
    }
    //      Create a subroutine called TurnTheCorner
    private void turnTheCorner()
    {
      //     that turns the Tortoise 1/3 of 360 degrees to the left
      Tortoise.turn(-360.0 / 3);
    }
    //      Create a subroutine called DrawASide
    private void drawASide()
    {
      //      that calls MoveTheLength and TurnTheCorner
      question4();
      question5();
    }
  }
  public void testCorrect() throws Exception
  {
    HousesQuizGrader.TURTLE_SPEED = Turtle.TEST_SPEED;
    new HousesQuizGrader().grade(new HousesCorrectQuiz());
    TortoiseUtils.verifyForOs();
  }
  public static class HousesIncorrectQuiz extends HousesQuizAdapter
  {
    private int length;
    private void small()
    {
      //        'The current length is 7
      length = 6;
    }
    //      'Create a subroutine called Medium
    private void medium()
    {
      //      ' that sets the current length to 21
    }
    //      'Create a subroutine called Large
    private void large()
    {
      //      ' that sets the current length to 63
      length = 100;
    }
    //      'Create a subroutine called MoveTheLength
    private void moveTheLength()
    {
      //      ' that moves the Tortoise the current length
      Tortoise.move(21);
    }
    //      'Create a subroutine called TurnTheCorner
    private void turnTheCorner()
    {
      //      ' that turns the Tortoise 1/3 of 360 degrees to the left
      Tortoise.turn(360.0 / 3);
    }
    //      'Create a subroutine called DrawASide
  }
  public void testIncorrect() throws Exception
  {
    HousesQuizGrader.TURTLE_SPEED = Turtle.TEST_SPEED;
    new HousesQuizGrader().grade(new HousesIncorrectQuiz());
    TortoiseUtils.verifyForOs();
  }
}
