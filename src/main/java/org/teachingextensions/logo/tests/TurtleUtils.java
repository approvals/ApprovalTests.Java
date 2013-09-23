package org.teachingextensions.logo.tests;

import org.teachingextensions.logo.Turtle;

public class TurtleUtils
{

  public static Turtle getTurtle()
  {
    Turtle turtle = new Turtle();
    turtle.setSpeed(Turtle.TEST_SPEED);
    return turtle;
  }
}
