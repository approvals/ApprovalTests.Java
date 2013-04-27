package org.teachingkidsprogramming.recipes.quizzes.graders;

import java.awt.Graphics2D;

import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Paintable;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.utils.TortoiseUtils;
import org.teachingextensions.windows.MessageBox;
import org.teachingextensions.windows.MessageBox.MessageBoxInstance;

public class HiLowQuizGrader implements Paintable
{
  private boolean[]                answers;
  public static int                TURTLE_SPEED = 9;
  public static MessageBoxInstance MESSAGE_BOX  = new MessageBoxInstance();
  private HiLowQuiz                quiz;
  private void displayScreen()
  {
    QuizUtils.prepareScoringScreen(answers, this, TURTLE_SPEED);
    drawRewardShape();
  }
  public void paint(Graphics2D g)
  {
    QuizUtils.displayScores(g, 300, answers);
  }
  private void drawRewardShape()
  {
    int[] angles = {
        90, -90, -90, 135, -45, 90, 90, -135, 90, 135, -90, 90, 90, 90, -90, -90, -90, -90, -90, 90, 90, 90, -90,
        90, 90, -90, -90, -90};
    int[] lengths = {
        70, 85, 25, 43, 40, 18, 26, 50, 113, 25, 85, 40, 30, 30, 20, 20, 20, 10, 20, 10, 10, 20, 10, 10, 30, 30,
        60, 183};
    Tortoise.setX(20);
    Tortoise.setPenColor(Colors.Greens.LawnGreen);
    for (int i = 0; i < lengths.length; i++)
    {
      Tortoise.turn(angles[i]);
      addChaos();
      Tortoise.move(lengths[i]);
    }
    quiz.question3();
  }
  private void addChaos()
  {
    reverseQuestion1();
    quiz.question1();
    reverseQuestion2();
    quiz.question2();
    reverseQuestion4();
    quiz.question4();
  }
  public void grade(HiLowQuiz quiz)
  {
    this.quiz = quiz;
    answers = new boolean[]{gradeQuestion1(), gradeQuestion2(), gradeQuestion3(), gradeQuestion4()};
    displayScreen();
  }
  private boolean gradeQuestion1()
  {
    return gradeQuestion1(115) && gradeQuestion1(114) && gradeQuestion1(116);
  }
  public boolean gradeQuestion1(int y)
  {
    TortoiseUtils.setTurtle(QuizUtils.createTestTurtle());
    Tortoise.setY(y);
    Tortoise.setAngle(1);
    quiz.question1();
    reverseQuestion1();
    boolean passed = Tortoise.getAngle() == 1;
    return passed;
  }
  private void reverseQuestion1()
  {
    if (Tortoise.getY() == 115)
    {
      Tortoise.turn(-63);
    }
  }
  private boolean gradeQuestion2()
  {
    return gradeQuestion2(10, 30) && gradeQuestion2(50, 30) && gradeQuestion2(30, 30);
  }
  public boolean gradeQuestion2(int x, int y)
  {
    TortoiseUtils.setTurtle(QuizUtils.createTestTurtle());
    Tortoise.setX(x);
    Tortoise.setY(y);
    Tortoise.setAngle(1);
    quiz.question2();
    reverseQuestion2();
    return Tortoise.getAngle() == 1;
  }
  private void reverseQuestion2()
  {
    if (Tortoise.getX() < Tortoise.getY())
    {
      Tortoise.turn(54);
    }
    else
    {
      Tortoise.turn(-22);
    }
  }
  private boolean gradeQuestion3()
  {
    MessageBoxMock mock = new MessageBoxMock();
    MessageBox.mock(mock);
    quiz.question3();
    MessageBox.mock(MESSAGE_BOX);
    return "elcomeway omehay!".equals(mock.lastMessage);
  }
  private boolean gradeQuestion4()
  {
    return gradeQuestion4(25) && gradeQuestion4(75) && gradeQuestion4(50);
  }
  public boolean gradeQuestion4(int y)
  {
    TortoiseUtils.setTurtle(QuizUtils.createTestTurtle());
    Tortoise.setAngle(1);
    Tortoise.setY(y);
    quiz.question4();
    reverseQuestion4();
    return Tortoise.getAngle() == 1;
  }
  private void reverseQuestion4()
  {
    if (Tortoise.getY() > 50)
    {
      Tortoise.turn(177);
    }
  }
}
