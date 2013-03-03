package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;

public class TriangleShell
{
  private static int length;
  public static void main(String[] args)
  {
    //    Show the tortoise --#1
    Tortoise.show();
    //    Make the tortoise go as fast as possible --#7
    Tortoise.setSpeed(10);
    length = 1;
    //    Do the following 60 times --#8
    for (int i = 0; i < 60; i++)
    {
      //       Change the color of the line the tortoise draws to a random color --#10
      Tortoise.setPenColor(Colors.getRandomColor());
      //       Increase the current length of the side by 4 pixels --#9
      length += 4;
      //       drawTriangle (recipe below) --#6
      drawTriangle();
      //       Turn the tortoise 1/60th of 360 degrees to the right --#11
      Tortoise.turn(306.0 / 60);
    }
    //      Repeat --#8
  }
  private static void drawTriangle()
  {
    //    ------------- Recipe for drawTriangle --#5
    //       Do the following 3 times --#3
    for (int i = 0; i < 3; i++)
    {
      //         Move the tortoise the current length of a side --#4
      Tortoise.move(length);
      //         Turn the tortoise 1/3rd of 360 degrees --#2
      Tortoise.turn(360.0 / 3);
    }
    //         Repeat --#3
    //      ------------- End of drawTriangle recipe --#5
  }
}
