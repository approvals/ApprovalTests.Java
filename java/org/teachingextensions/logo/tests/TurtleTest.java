package org.teachingextensions.logo.tests;

import java.awt.Color;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.lambda.functions.Function1;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Turtle;
import org.teachingextensions.logo.Wheel;

@UseReporter({DiffReporter.class, DelayedClipboardReporter.class})
public class TurtleTest extends TestCase
{
  public void testShow() throws Exception
  {
    Turtle turtle = TurtleUtils.getTurtle();
    Approvals.verify(turtle.getImage());
  }
  public void testRotate() throws Exception
  {
    Turtle turtle = TurtleUtils.getTurtle();
    turtle.turnRight();
    Approvals.verify(turtle.getImage());
  }
  public void testRotate45() throws Exception
  {
    Turtle turtle = TurtleUtils.getTurtle();
    turtle.turn(45);
    Approvals.verify(turtle.getImage());
    assertEquals(45.0, turtle.getAngleInDegrees(), 0.02);
  }
  public void testRotate45CounterClockwise() throws Exception
  {
    Turtle turtle = TurtleUtils.getTurtle();
    turtle.turn(-45);
    Approvals.verify(turtle.getImage());
  }
  public void testMove() throws Exception
  {
    Turtle turtle = TurtleUtils.getTurtle();
    turtle.move(50);
    Approvals.verify(turtle.getImage());
  }
  public void testMove45DegreesLeft() throws Exception
  {
    Turtle turtle = TurtleUtils.getTurtle();
    turtle.turn(-45);
    turtle.move(50);
    Approvals.verify(turtle.getImage());
  }
  public void testDelta() throws Exception
  {
    String out = "";
    for (int i = 0; i < 9; i++)
    {
      double deltaY = Turtle.getDeltaY(5, i * 45.0);
      double deltaX = Turtle.getDeltaX(5, i * 45.0);
      out += String.format("%s=>[%s, %s]\r\n", i * 45, deltaX, deltaY);
    }
    Approvals.verify(out);
  }
  public void testPentagonCrazy() throws Exception
  {
    Turtle turtle = TurtleUtils.getTurtle();
    Wheel<Color> wheel = new Wheel<Color>();
    wheel.add(Colors.Blues.SteelBlue);
    wheel.add(Colors.Purples.DarkMagenta);
    wheel.add(Colors.Blues.DarkSlateBlue);
    wheel.add(Colors.Purples.Indigo);
    wheel.add(Colors.Purples.DarkOrchid);
    Wheel<Integer> thickness = new Wheel<Integer>(1, 2, 3);
    for (int i = 0; i < 200; i++)
    {
      turtle.setPenColor(wheel.next());
      turtle.setPenWidth(thickness.next());
      turtle.move(i + 1);
      turtle.turn(360 / 5);
      turtle.turn(1);
    }
    Approvals.verify(turtle.getImage());
  }
  public void testPenDown() throws Exception
  {
    Turtle turtle = TurtleUtils.getTurtle();
    for (int i = 0; i < 3; i++)
    {
      turtle.move(10);
      turtle.penUp();
      turtle.move(10);
      turtle.penDown();
    }
    Approvals.verify(turtle.getImage());
  }
  public void testHide() throws Exception
  {
    Turtle turtle = TurtleUtils.getTurtle();
    turtle.move(50);
    turtle.hide();
    Approvals.verify(turtle.getImage());
  }
  public void testSpeed() throws Exception
  {
    Integer[] speeds = {-5, 5, 15, Turtle.TEST_SPEED};
    Approvals.verifyAll("Speeds", speeds, new Function1<Integer, String>()
    {
      @Override
      public String call(Integer speed)
      {
        try
        {
          Turtle turtle = TurtleUtils.getTurtle();
          turtle.setSpeed(speed);
          return speed + " => " + turtle.getSpeed();
        }
        catch (Exception e)
        {
          return speed + " => " + e.getMessage();
        }
      }
    });
  }
}
