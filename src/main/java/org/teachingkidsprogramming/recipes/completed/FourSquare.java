package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;

public class FourSquare
{
  public static void main(String[] args)
  {
    //  Show the tortoise --#1
    Tortoise.show();
    //  Make the tortoise move as fast as possible --#8
    Tortoise.setSpeed(10);
    //  Do the following 4 times --#9
    for (int i = 0; i < 4; i++)
    {
      //     DrawSquare (recipe below) --#7
      drawSquare();
      //     Turn the tortoise 90 degrees to the right --#10
      Tortoise.turn(90);
    }
  }
  private static void drawSquare()
  {
    //  ------------- Recipe for DrawSquare --#6
    //     Do the following 4 times --#5
    for (int i = 0; i < 4; i++)
    {
      //       Change the color of the line the tortoise draws to a random color --#3
      Tortoise.setPenColor(Colors.getRandomColor());
      //       Move the tortoise 50 pixels --#2
      Tortoise.move(50);
      //       Turn the tortoise 90 degrees to the right --#4
      Tortoise.turn(90);
    }
    //    ------------- End of DrawSquare recipe --#6
  }
}
