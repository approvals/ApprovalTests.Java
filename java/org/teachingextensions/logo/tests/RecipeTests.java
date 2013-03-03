package org.teachingextensions.logo.tests;

import java.awt.Color;
import java.util.HashMap;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.FileLauncherReporter;
import org.approvaltests.reporters.UseReporter;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Turtle;
import org.teachingextensions.logo.Wheel;

@UseReporter({FileLauncherReporter.class, DelayedClipboardReporter.class})
public class RecipeTests extends TestCase
{
  public void testSimpleSquare() throws Exception
  {
    Turtle turtle = new Turtle();
    turtle.setSpeed(Turtle.TEST_SPEED);
    turtle.show();
    for (int i = 0; i < 4; i++)
    {
      turtle.setPenColor(Colors.Blues.SteelBlue);
      turtle.move(50);
      turtle.turn(90);
    }
    Approvals.verify(turtle.getImage());
  }
  public void testCircle() throws Exception
  {
    Turtle turtle = new Turtle();
    turtle.setSpeed(Turtle.TEST_SPEED);
    turtle.show();
    int sides = 35;
    for (int i = 0; i < sides; i++)
    {
      turtle.setPenColor(Colors.Reds.OrangeRed);
      turtle.move(18);
      turtle.turn(360.0 / sides);
    }
    Approvals.verify(turtle.getImage());
  }
  public void testSpiral() throws Exception
  {
    Color color = Colors.Purples.Violet;
    Turtle turtle = new Turtle();
    turtle.setSpeed(Turtle.TEST_SPEED);
    for (int i = 0; i < 25; i++)
    {
      turtle.setPenColor(color);
      turtle.move(3 * i);
      turtle.turnRight();
      color = Colors.darken(color);
    }
    Approvals.verify(turtle.getImage());
  }
  public void testFourSquare() throws Exception
  {
    Turtle turtle = new Turtle();
    Colors.mockRandom();
    turtle.setSpeed(Turtle.TEST_SPEED);
    for (int i = 0; i < 4; i++)
    {
      drawSquare(turtle);
      turtle.turnRight();
    }
    Approvals.verify(turtle.getImage());
  }
  private void drawSquare(Turtle turtle)
  {
    for (int i = 0; i < 4; i++)
    {
      turtle.setPenColor(Colors.getRandomColor());
      turtle.move(50);
      turtle.turn(90);
    }
  }
  public void testDigiGirlzFlower() throws Exception
  {
    Turtle turtle = new Turtle();
    turtle.setSpeed(Turtle.TEST_SPEED);
    turtle.show();
    turtle.getBackgroundWindow().setColor(Colors.Grays.Silver);
    Wheel<Color> palette = setupColors();
    turtle.setPenWidth(3);
    for (int i = 0; i < 15; i++)
    {
      drawOctogon(turtle, palette);
      turtle.turn(360 / 15);
    }
    Approvals.verify(turtle.getImage());
  }
  private void drawOctogon(Turtle turtle, Wheel<Color> palette)
  {
    for (int i = 0; i < 8; i++)
    {
      turtle.setPenColor(palette.next());
      turtle.move(50);
      turtle.turn(360 / 8);
    }
  }
  private Wheel<Color> setupColors()
  {
    Color[] colors = {Colors.Reds.Red, Colors.Oranges.DarkOrange, Colors.Yellows.Gold, Colors.Yellows.Yellow};
    Wheel<Color> wheel = new Wheel<Color>();
    wheel.add(colors[0]);
    wheel.add(colors[1]);
    wheel.add(colors[2]);
    wheel.add(colors[3]);
    wheel.add(colors[3]);
    wheel.add(colors[2]);
    wheel.add(colors[1]);
    wheel.add(colors[0]);
    return wheel;
  }
  public void testTriangleShell() throws Exception
  {
    Turtle turtle = new Turtle();
    turtle.setSpeed(Turtle.TEST_SPEED);
    turtle.show();
    int number = 60;
    int length = 0;
    for (int i = 0; i < number; i++)
    {
      turtle.setPenColor(Colors.getRandomColor());
      length += 4;
      drawTriangle(turtle, length);
      turtle.turn(360 / number);
    }
    Approvals.verify(turtle.getImage());
  }
  private void drawTriangle(Turtle turtle, int length)
  {
    int sides = 3;
    for (int i = 0; i < sides; i++)
    {
      turtle.move(length);
      turtle.turn(360 / sides);
    }
  }
  public void testKnottedRing() throws Exception
  {
    Turtle turtle = new Turtle();
    turtle.setSpeed(Turtle.TEST_SPEED);
    Wheel<Color> wheel = new Wheel<Color>(Colors.Pinks.HotPink, Colors.Reds.Red, Colors.Purples.Fuchsia,
        Colors.Oranges.OrangeRed, Colors.Pinks.DeepPink, Colors.Pinks.MediumVioletRed, Colors.Reds.Crimson,
        Colors.Oranges.Tomato);
    for (int i = 0; i < 30; i++)
    {
      turtle.setPenColor(wheel.next());
      drawOctogonWithOverlap(turtle);
      turtle.turn(360 / 30);
      turtle.turn(5);
    }
    Approvals.verify(turtle.getImage());
  }
  private void drawOctogonWithOverlap(Turtle turtle)
  {
    for (int i = 0; i < 9; i++)
    {
      turtle.move(110);
      turtle.turn(360 / 8);
    }
  }
  public void testTurtleTree() throws Exception
  {
    Turtle turtle = new Turtle();
    turtle.setSpeed(Turtle.TEST_SPEED);
    turtle.show();
    turtle.getBackgroundWindow().setColor(Colors.Grays.Black);
    drawBranch(turtle, 60);
    Approvals.verify(turtle.getImage());
  }
  private void drawBranch(Turtle turtle, int length)
  {
    pickColorForBranchLenth(turtle, length);
    turtle.move(length);
    drawLowerBranch(turtle, length - 10);
    pickColorForBranchLenth(turtle, length);
    turtle.move(-length);
  }
  private void drawLowerBranch(Turtle turtle, int length)
  {
    if (length > 0)
    {
      turtle.turn(30);
      drawBranch(turtle, length);
      turtle.turn(-60);
      drawBranch(turtle, length);
      turtle.turn(30);
    }
  }
  private void pickColorForBranchLenth(Turtle turtle, int length)
  {
    HashMap<Integer, Color> map = new HashMap<Integer, Color>();
    map.put(10, Colors.Greens.LimeGreen);
    map.put(20, Colors.Greens.ForestGreen);
    map.put(30, Colors.Greens.DarkGreen);
    map.put(40, Colors.Greens.Olive);
    map.put(50, Colors.Browns.Sienna);
    map.put(60, Colors.Browns.SaddleBrown);
    turtle.setPenColor(map.get(length));
  }
}
