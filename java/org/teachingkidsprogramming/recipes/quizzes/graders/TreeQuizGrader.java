package org.teachingkidsprogramming.recipes.quizzes.graders;

import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.teachingextensions.logo.Paintable;
import org.teachingextensions.logo.Turtle;
import org.teachingextensions.logo.utils.TortoiseUtils;

import com.spun.util.NumberUtils;

public class TreeQuizGrader implements Paintable
{
  private boolean[]       answers;
  public static int       TURTLE_SPEED = 9;
  private TreeQuizAdapter quiz;
  private void displayScreen()
  {
    QuizUtils.prepareScoringScreen(answers, this, TURTLE_SPEED);
    drawRewardShape();
  }
  public void grade(TreeQuizAdapter quiz)
  {
    this.quiz = quiz;
    answers = new boolean[]{grade1DoubleLength(), grade2DecreaseTurn(), grade3SetNinety(), grade4moveTheLength()};
    displayScreen();
  }
  public void paint(Graphics2D g, JPanel caller)
  {
    QuizUtils.displayScores(g, 300, answers);
  }
  public void drawRewardShape()
  {
    TortoiseUtils.setOrientation(100, 75, 90);
  }
  private boolean grade1DoubleLength()
  {
    quiz.length = 111;
    quiz.question1();
    return quiz.length == 222;
  }
  private boolean grade2DecreaseTurn()
  {
    quiz.turn = -31;
    quiz.question2();
    return -32 == quiz.turn;
  }
  private boolean grade3SetNinety()
  {
    quiz.turn = 25;
    quiz.question3();
    return 90 == quiz.turn;
  }
  private boolean grade4moveTheLength()
  {
    Turtle turtle = QuizUtils.createTestTurtle();
    TortoiseUtils.setTurtle(turtle);
    TortoiseUtils.setOrientation(0, 0, 90);
    quiz.length = -3;
    quiz.question4();
    return NumberUtils.equals(TortoiseUtils.getTurtle().getX(), -3, 0.005);
  }
}
