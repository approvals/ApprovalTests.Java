package org.teachingkidsprogramming.recipes.completed;

import java.awt.Color;

import org.teachingextensions.logo.ColorWheel;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;

public class DigiFlower
{
  public static void main(String[] args)
  {
    //    Show the tortoise --#1
    Tortoise.show();
    //    Make the tortoise move as fast as possible --#7
    Tortoise.setSpeed(10);
    //    Make the background silver --#8
    Tortoise.getBackgroundWindow().setBackground(Colors.Grays.Silver);
    //    Make the line the tortoise draws 3 pixels wide --#20
    Tortoise.setPenWidth(3);
    //    CreateColorPalette (recipe below) --#9
    createColorPalette();
    //    Do the following 15 times --#19
    for (int i = 1; i <= 15; i++)
    {
      //    DrawOctogon (recipe below) --#10
      drawOctogon();
      //     Turn the tortoise 1/15th of 360 degrees to the right --#18
      Tortoise.turn(360.0 / 15);
    }
  }
  //    ------------- Recipe for CreateColorPalette --#9
  private static void createColorPalette()
  {
    //     Color 1 is red --#3
    Color color1 = Colors.Reds.Red;
    //     Color 2 is dark orange --#11
    Color color2 = Colors.Oranges.DarkOrange;
    //     Color 3 is gold --#12
    Color color3 = Colors.Yellows.Gold;
    //     Color 4 is yellow --#13
    Color color4 = Colors.Yellows.Yellow;
    //     Add color 1 to the color wheel --#3.1
    ColorWheel.addColor(color1);
    //     Add color 2 to the color wheel --#11.1
    ColorWheel.addColor(color2);
    //     Add color 3 to the color wheel --#12.1
    ColorWheel.addColor(color3);
    //     Add color 4 to the color wheel --#13.1
    ColorWheel.addColor(color4);
    //     Add color 4 to the color wheel --#14
    ColorWheel.addColor(color4);
    //     Add color 3 to the color wheel --#15
    ColorWheel.addColor(color3);
    //     Add color 2 to the color wheel --#16
    ColorWheel.addColor(color2);
    //     Add color 1 to the color wheel --#17
    ColorWheel.addColor(color1);
    //
  }
  //    ------------- Recipe for DrawOctogon --#10
  private static void drawOctogon()
  {
    //     Do the following 8 times --#6
    for (int i = 1; i <= 8; i++)
    {
      //     Change the color of the line the tortoise draws to the next color on the color wheel --#4
      Tortoise.setPenColor(ColorWheel.getNextColor());
      //     Move the tortoise 50 pixels --#2
      Tortoise.move(50);
      //     Turn the tortoise 1/8th of 360 degrees to the right --#5
      Tortoise.turn(360.0 / 8);
    }
  }
}
