package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.Turtle.Animals;

public class SpiderWeb
{
  public static void main(String[] args)
  {
    //    Make the tortoise go as fast as possible --#6
    Tortoise.setSpeed(10);
    //    Change the width of the line the tortoise draws to 1 pixel --#12
    Tortoise.setPenWidth(1);
    //    Change the Tortoise to a Spider --#14
    Tortoise.setAnimal(Animals.Spider);
    //    Change the color of the line the tortoise draws to silver --#13
    Tortoise.setPenColor(Colors.Grays.Silver);
    //    The current length of a line is 10 pixels --#1.2                  
    int length = 10;
    //    The current zoom is 1.1 --#8.2
    double zoom = 1.1;
    //    Do the following 10 times --#10
    for (int i = 1; i <= 10; i++)
    {
      //     WeaveOneLayer (recipe below) --#9
      length = weaveOneLayer(length, zoom);
      //     Change the zoom so it is multiplied by 1.3 --#11
      zoom *= 1.3;
      //    Repeat
    }
  }
  //    ------------- Recipe for WeaveOneLayer --#9
  public static int weaveOneLayer(int length, double zoom)
  {
    //    Do the following 6 times --#5
    for (int i = 1; i <= 6; i++)
    {
      //     DrawTriangle (recipe below) --#4.2
      drawTriangle(length);
      //     Turn the tortoise 1/6th of 360 degrees to the right --#7
      Tortoise.turn(360.0 / 6);
      //     Increase the length of the line by the current zoom --#8.1
      length += zoom;
      //    Repeat
    }
    return length;
  }
  //    ------------- End of WeaveOneLayer recipe --#9
  //    ------------- Recipe for DrawTriangle --#4
  public static void drawTriangle(int length)
  {
    //    Do the following 3 times --#3
    for (int i = 1; i <= 3; i++)
    {
      //     Move the tortoise the length of a line --#1.1
      Tortoise.move(length);
      //     Turn the tortoise 1/3rd of 360 degrees --#2
      Tortoise.turn(360.0 / 3);
      //    Repeat
    }
  }
  //    ------------- End of DrawTriangle recipe --#4
}
