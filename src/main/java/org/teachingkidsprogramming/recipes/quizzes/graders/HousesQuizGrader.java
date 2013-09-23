package org.teachingkidsprogramming.recipes.quizzes.graders;

import java.awt.Graphics2D;

import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Paintable;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.Turtle;
import org.teachingextensions.logo.utils.TortoiseUtils;

import com.spun.util.NumberUtils;

public class HousesQuizGrader implements Paintable
{
  private boolean[]  answers;
  public static int  TURTLE_SPEED = 9;
  private HousesQuiz quiz;
  private void displayScreen()
  {
    QuizUtils.prepareScoringScreen(answers, this, TURTLE_SPEED);
    drawRewardShape();
  }
  public void grade(HousesQuiz quiz)
  {
    this.quiz = quiz;
    answers = new boolean[]{grade1Small(),
        grade2Medium(),
        grade3Large(),
        grade4moveTheLength(),
        grade5turnTheCorner(),
        grade6drawASide()};
    displayScreen();
  }
  public void paint(Graphics2D g)
  {
    QuizUtils.displayScores(g, 300, answers);
  }
  public void drawRewardShape()
  {
    TortoiseUtils.setOrientation(100, 75, 90);
    for (int i = 0; i < 6; i++)
    {
      drawTriangle();
    }
  }
  private boolean grade1Small()
  {
    quiz.question1();
    return 7 == quiz.length;
  }
  private boolean grade2Medium()
  {
    quiz.medium();
    return 21 == quiz.length;
  }
  private boolean grade3Large()
  {
    quiz.large();
    return 63 == quiz.length;
  }
  private boolean grade4moveTheLength()
  {
    Turtle turtle = QuizUtils.createTestTurtle();
    TortoiseUtils.setTurtle(turtle);
    TortoiseUtils.setOrientation(0, 0, 90);
    quiz.length = -3;
    quiz.moveTheLength();
    return NumberUtils.equals(TortoiseUtils.getTurtle().getX(), -3, 0.005);
  }
  private boolean grade5turnTheCorner()
  {
    int angle = NumberUtils.getRandomInt(130, 300);
    Turtle turtle = QuizUtils.createTestTurtle();
    TortoiseUtils.setTurtle(turtle);
    turtle.setAngleInDegrees(angle);
    quiz.turnTheCorner();
    return NumberUtils.equals(turtle.getAngleInDegrees(), angle + (-360.0 / 3), 0.005);
  }
  private boolean grade6drawASide()
  {
    int angle = 180;
    Turtle turtle = QuizUtils.createTestTurtle();
    TortoiseUtils.setTurtle(turtle);
    TortoiseUtils.setOrientation(0, 0, angle);
    quiz.length = -3;
    quiz.drawASide();
    boolean move = NumberUtils.equals(TortoiseUtils.getTurtle().getY(), -3, 0.005);
    boolean turn = NumberUtils.equals(turtle.getAngleInDegrees(), angle + (-360.0 / 3), 0.005);
    return move && turn;
  }
  private void drawTriangle()
  {
    Tortoise.penUp();
    quiz.medium();
    quiz.large();
    Tortoise.move(quiz.length);
    Tortoise.penDown();
    quiz.turnTheCorner();
    for (int i = 0; i < 2; i++)
    {
      Tortoise.setPenWidth(3);
      Tortoise.setPenColor(Colors.Purples.Lavender);
      quiz.large();
      quiz.moveTheLength();
      Tortoise.turn(180);
      quiz.moveTheLength();
      Tortoise.turn(180);
      drawSmallerLine();
      quiz.turnTheCorner();
    }
    Tortoise.penUp();
    quiz.large();
    quiz.moveTheLength();
    Tortoise.penDown();
    Tortoise.turn(180);
    quiz.turnTheCorner();
  }
  private void drawSmallerLine()
  {
    moveMedium();
    Tortoise.turn(360.0 / 6);
    moveMedium();
    quiz.turnTheCorner();
    moveMedium();
    quiz.turnTheCorner();
    Tortoise.turn(180);
    moveMedium();
  }
  private void moveMedium()
  {
    quiz.medium();
    Tortoise.setPenWidth(2);
    Tortoise.setPenColor(Colors.Blues.LightBlue);
    quiz.moveTheLength();
    Tortoise.turn(180);
    quiz.moveTheLength();
    Tortoise.turn(180);
    drawSmallestLine();
  }
  private void drawSmallestLine()
  {
    Tortoise.setPenWidth(1);
    Tortoise.setPenColor(Colors.Blues.PowderBlue);
    quiz.question1();
    quiz.moveTheLength();
    Tortoise.turn(360.0 / 6);
    quiz.drawASide();
    quiz.drawASide();
    Tortoise.turn(180);
    quiz.moveTheLength();
  }
}
