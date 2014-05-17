package org.teachingkidsprogramming.recipes.quizzes.tests;

import junit.framework.TestCase;

import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.teachingextensions.logo.Turtle;
import org.teachingextensions.logo.utils.TortoiseUtils;
import org.teachingkidsprogramming.recipes.quizzes.graders.TreeQuizAdapter;
import org.teachingkidsprogramming.recipes.quizzes.graders.TreeQuizGrader;

@UseReporter({DelayedClipboardReporter.class, DiffReporter.class})
public class TreeQuizTest extends TestCase
{
  public static class TreeCorrectQuiz extends TreeQuizAdapter
  {
    //  Sub question1
    private void doubleLength()
    {
      // make the current length twice what it is
      length = length * 2;
    }
    //      EndSub
    //Question2
    // create a sub called DecreaseTurn which 
    private void decreaseTurn()
    {
      turn = turn - 1;
    }
    // decrease the current turn by 1
    //Question3
    //create a sub called SetNinety which 
    private void setNinety()
    {
      // sets the angle of the current turn to 90
      turn = 90;
    }
    //Question4
    //create a sub called AngleFive which 
    private void angleFive()
    {
    }
    // sets the 5th angle to 36 degrees
  }
  public void testCorrect() throws Exception
  {
    TreeQuizGrader.TURTLE_SPEED = Turtle.TEST_SPEED;
    new TreeQuizGrader().grade(new TreeCorrectQuiz());
    TortoiseUtils.verifyForOs();
  }
  //  public static class TreeIncorrectQuiz extends TreeQuiz
  //  {
  //    @Override
  //    public void question1()
  //    {
  //    }
  //    @Override
  //    public void question2()
  //    {
  //    }
  //    @Override
  //    public void question3()
  //    {
  //    }
  //    @Override
  //    public void question4()
  //    {
  //    }
  //  }
  //  public void testIncorrect() throws Exception
  //  {
  //    TreeQuizGrader.TURTLE_SPEED = Turtle.TEST_SPEED;
  //    new TreeQuizGrader().grade(new TreeIncorrectQuiz());
  //    TortoiseUtils.verifyForOs();
  //  }
}
