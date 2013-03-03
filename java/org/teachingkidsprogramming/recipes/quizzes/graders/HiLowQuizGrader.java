package org.teachingkidsprogramming.recipes.quizzes.graders;

import java.awt.Graphics2D;

import org.teachingextensions.logo.Paintable;
import org.teachingextensions.logo.utils.TortoiseUtils;

public class HiLowQuizGrader implements Paintable
{
  private boolean[] answers;
  public static int TURTLE_SPEED = 9;
  private HiLowQuiz quiz;
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
  }
  public void grade(HiLowQuiz quiz)
  {
    this.quiz = quiz;
    answers = new boolean[]{gradeQuestion1(), gradeQuestion2(), gradeQuestion3(), gradeQuestion4()};
    displayScreen();
  }
  private boolean gradeQuestion1()
  {
    TortoiseUtils.setTurtle(QuizUtils.createTestTurtle());
    quiz.question1();
    return false;
  }
  private boolean gradeQuestion2()
  {
    TortoiseUtils.setTurtle(QuizUtils.createTestTurtle());
    quiz.question2();
    return false;
  }
  private boolean gradeQuestion3()
  {
    TortoiseUtils.setTurtle(QuizUtils.createTestTurtle());
    quiz.question3();
    return false;
  }
  private boolean gradeQuestion4()
  {
    TortoiseUtils.setTurtle(QuizUtils.createTestTurtle());
    quiz.question4();
    return false;
  }
}
