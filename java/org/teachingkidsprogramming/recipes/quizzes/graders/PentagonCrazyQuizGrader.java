package org.teachingkidsprogramming.recipes.quizzes.graders;

import java.awt.Color;
import java.awt.Graphics2D;

import org.teachingextensions.logo.ColorWheel;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Paintable;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.Turtle;
import org.teachingextensions.logo.Wheel;
import org.teachingextensions.logo.utils.TortoiseUtils;

import com.spun.util.NumberUtils;

public class PentagonCrazyQuizGrader implements Paintable
{
  private boolean[]           answers;
  public static int           TURTLE_SPEED    = 9;
  private PentagonQuizAdapter quiz;
  private boolean             mockStitch      = false;
  private int                 count           = 0;
  private String              stitchingAngles = "25.601294645002;39.9954094294277;42.2747487414833;47.092558690198;-2.788677126170;8.082119014691;1.677864557623;3.746613109730;-25.826803494618;-67.5809589960839;-44.8780585363725;6.8440829952860;-18.9074111387679;-18.3392015893888;-22.0596908668138;-54.1385093815182;-73.2696242839109;-29.575128866738;-31.059191548109;-10.233377619284;3.083410700787;2.177224859757;12.620249199431;10.091222647431;-2.514163030606;-0.619322986136;19.377048562492;3.967384838321;20.365744867635;8.924468620782;5.247906731382;38.2861906270044;53.6722312927899;33.3231228793197;21.8612545917246;18.7599063446845;-1.9345991831263;11.1144118699626;-4.3183191159596;-0.4578370883930;20.6609500932546;-3.8233412487789;-32.1839398800777;17.4765102360018;28.6786604159701;-1.1959813284483;-5.7224879538386;-10.3016495267062;-63.9467251702219;-63.8779449224642;-30.3504986035853;-19.8687944708732;-21.978159282607;-14.363027757691;-16.463967303497;-23.821840937835;0.926592976539;-39.940321197861;-31.972205891899;-19.866181322959;0.031467825853;-33.734964949504;-9.726424741340;8.374963042147;5.816868521146;-8.073061677634;-3.548073295589;117.947684481056;2.034836317127;11.764854710885;19.126654192579;10.631238252889;28.914377802640;19.522477772099;28.4952954806338;32.0543585645222";
  private Wheel<Double>       wheel;
  private void displayScreen()
  {
    QuizUtils.prepareScoringScreen(answers, this, TURTLE_SPEED);
    drawRewardShape();
  }
  public void grade(PentagonQuizAdapter quiz)
  {
    quiz.grader = this;
    this.quiz = quiz;
    answers = new boolean[]{gradeThread(), grade2Stitch76(), grade3Lime(), grade4Red()};
    displayScreen();
  }
  public void paint(Graphics2D g)
  {
    QuizUtils.displayScores(g, 300, answers);
  }
  private void getAngles()
  {
    String[] pieces = stitchingAngles.split(";");
    wheel = new Wheel<Double>();
    for (String piece : pieces)
    {
      wheel.add(Double.parseDouble(piece));
    }
  }
  private void drawRewardShape()
  {
    getAngles();
    TortoiseUtils.setOrientation(60, 80, 0);
    setColors();
    quiz.question2();
  }
  private boolean gradeThread()
  {
    Turtle turtle = QuizUtils.createTestTurtle();
    TortoiseUtils.setTurtle(turtle);
    TortoiseUtils.setOrientation(0, 0, 90);
    quiz.callThread();
    return NumberUtils.equals(TortoiseUtils.getTurtle().getX(), 6, 0.005);
  }
  private boolean grade2Stitch76()
  {
    mockStitch = true;
    quiz.question2();
    mockStitch = false;
    return count == 76;
  }
  private boolean grade3Lime()
  {
    ColorWheel.removeAllColors();
    quiz.question3();
    return getSafeColor() == Colors.Greens.Lime;
  }
  public Color getSafeColor()
  {
    try
    {
      return ColorWheel.getNextColor();
    }
    catch (Exception e)
    {
      return null;
    }
  }
  private boolean grade4Red()
  {
    ColorWheel.removeAllColors();
    quiz.question4();
    return getSafeColor() == Colors.Reds.Red;
  }
  public void setColors()
  {
    ColorWheel.removeAllColors();
    quiz.question3();
    quiz.question4();
  }
  public void stitch()
  {
    if (mockStitch)
    {
      count++;
    }
    else
    {
      Tortoise.setPenColor(getSafeColor());
      Tortoise.turn(wheel.next());
      Tortoise.penDown();
      quiz.callThread();
      Tortoise.penUp();
      quiz.callThread();
    }
  }
}
