package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;

public class SimpleSquare
{
  public static void main(String[] args) throws Exception
  {
    //  Show the tortoise --#1
    Tortoise.show();
    //  Make the tortoise move as fast as possible --#6
    Tortoise.setSpeed(10);
    //  Do the following 4 times --#5.1
    for (int i = 1; i <= 4; i++)
    {
      //      Change the color of the line the tortoise draws to "blue" --#4
      Tortoise.setPenColor(Colors.Blues.Blue);
      //      Move the tortoise 50 pixels --#2
      Tortoise.move(50);
      //      Turn the tortoise to the right (90 degrees) --#3
      Tortoise.turn(90);
      //  Repeat --#5.2
    }
  }
}
