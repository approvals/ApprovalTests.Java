package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.logo.ColorWheel;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;

public class Spiral
{
  public static void main(String[] args)
  {
    //    Show the tortoise --#1
    Tortoise.show();
    //    Make the tortoise go as fast as possible --#4
    Tortoise.setSpeed(10);
    //    Add Blue Violet to the Color Wheel --#7
    ColorWheel.addColor(Colors.Purples.BlueViolet);
    //    Add Violet to the Color Wheel --#8
    ColorWheel.addColor(Colors.Purples.Violet);
    //    Add Purple to the Color Wheel --#9
    ColorWheel.addColor(Colors.Purples.Purple);
    //    Do the following 75 times --#3
    for (int i = 0; i < 75; i++)
    {
      //         Change the color of the line the tortoise draws the next color on the Color Wheel --#6
      Tortoise.setPenColor(ColorWheel.getNextColor());
      //         Move the tortoise 5 times the current line number you are drawing --#5
      Tortoise.move(i * 5);
      //         Turn the tortoise 1/3 of 360 degrees to the right --#2
      Tortoise.turn(360.0 / 3);
    }
  }
}
