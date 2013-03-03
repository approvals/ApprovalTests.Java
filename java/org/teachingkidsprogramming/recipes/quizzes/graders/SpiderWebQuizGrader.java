package org.teachingkidsprogramming.recipes.quizzes.graders;

import java.awt.Graphics2D;

import org.teachingextensions.logo.ColorWheel;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Paintable;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.utils.TortoiseUtils;

public class SpiderWebQuizGrader implements Paintable
{
  public static int  TURTLE_SPEED = 10;
  private boolean[]  answers;
  private SpiderQuiz quiz;
  private boolean    mock;
  private int        circleCount;
  private int        adjustCount;
  private int        angle        = -15;
  private void displayScreen()
  {
    QuizUtils.prepareScoringScreen(answers, this, TURTLE_SPEED);
    drawRewardShape();
  }
  private void drawRewardShape()
  {
    initialize(quiz);
    quiz.question1();
    quiz.circleAround();
    Tortoise.hide();
  }
  public void initialize(SpiderQuiz quiz)
  {
    mock = false;
    TortoiseUtils.setOrientation(150, 200, 0);
    Tortoise.setPenWidth(15);
    ColorWheel.removeAllColors();
    ColorWheel.addColor(Colors.Grays.Black);
    ColorWheel.addColor(Colors.Whites.White);
    Tortoise.getBackgroundWindow().setBackground(Colors.Grays.Silver);
    quiz.number = 16;
    quiz.length = 15;
  }
  public void grade(SpiderQuiz quiz)
  {
    quiz.grader = this;
    this.quiz = quiz;
    answers = new boolean[]{gradeQuestion1(), grade2CircleAround(), grade3Grow(), grade4Shrink(), grade5Expand()};
    displayScreen();
  }
  private boolean gradeQuestion1()
  {
    mock = true;
    circleCount = 0;
    quiz.number = 11;
    quiz.question1();
    return circleCount == 11;
  }
  private boolean grade2CircleAround()
  {
    mock = true;
    circleCount = 0;
    adjustCount = 0;
    quiz.number = 4;
    quiz.circleAround();
    return adjustCount == 3 && circleCount == 12;
  }
  private boolean grade3Grow()
  {
    quiz.length = 10.0;
    quiz.grow();
    return quiz.length == 25.0;
  }
  private boolean grade4Shrink()
  {
    quiz.length = -10;
    quiz.shrink();
    return quiz.length == -19;
  }
  private boolean grade5Expand()
  {
    quiz.number = -112;
    quiz.expand();
    return quiz.number == -100;
  }
  public void paint(Graphics2D g)
  {
    QuizUtils.displayScores(g, 400, answers);
  }
  public void circle(SpiderQuiz quiz)
  {
    if (mock)
    {
      circleCount++;
    }
    else
    {
      Tortoise.setPenColor(ColorWheel.getNextColor());
      Tortoise.turn(angle);
      Tortoise.move(quiz.length);
      Tortoise.penUp();
      Tortoise.move(-quiz.length);
      Tortoise.turn(-angle);
      Tortoise.move(quiz.length);
      quiz.shrink();
      Tortoise.move(quiz.length);
      quiz.grow();
      Tortoise.penDown();
      Tortoise.turn(360.0 / quiz.number);
    }
  }
  public void adjust()
  {
    if (mock)
    {
      adjustCount++;
    }
    else
    {
      angle *= -1;
      Tortoise.setPenColor(ColorWheel.getNextColor());
      Tortoise.turn(-90);
      Tortoise.penUp();
      Tortoise.move(42);
      Tortoise.penDown();
      Tortoise.turn(90);
      quiz.expand();
    }
  }
}
