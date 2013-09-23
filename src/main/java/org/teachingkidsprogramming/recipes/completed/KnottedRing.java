package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.logo.ColorWheel;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;

public class KnottedRing
{
  public static void main(String[] args)
  {
    //    Make the tortoise move as fast as possible --#4
    Tortoise.setSpeed(10);
    //   createColorPalette (recipe below) --#6
    createColorPalette();
    //    Do the following 30 times --#10
    for (int i = 0; i < 30; i++)
    {
      //       Change the color of the line the tortoise draws to a random color from the color wheel --#5
      Tortoise.setPenColor(ColorWheel.getNextColor());
      //       drawOctagonWithOverlap (recipe below) --#8
      drawOctagonWithOverlap();
      //       Turn the tortoise 1/30th of 360 degrees to the right --#9
      Tortoise.turn(360.0 / 30);
      //       Turn the tortoise 5 more degrees to the right --#11
      Tortoise.turn(5);
      //      Repeat --#10
    }
  }
  private static void createColorPalette()
  {
    //    ------------- Recipe for createColorPalette --#6
    //       Add hot pink to the color wheel --#6
    ColorWheel.addColor(Colors.Pinks.HotPink);
    //       Add red to the color wheel --#12
    ColorWheel.addColor(Colors.Reds.Red);
    //       Add fuchsia to the color wheel --#13
    ColorWheel.addColor(Colors.Pinks.Fuchsia);
    //       Add orange red to the color wheel --#14
    ColorWheel.addColor(Colors.Reds.OrangeRed);
    //       Add deep pink to the color wheel --#15
    ColorWheel.addColor(Colors.Pinks.DeepPink);
    //       Add medium violet red to the color wheel --#16
    ColorWheel.addColor(Colors.Reds.MediumVioletRed);
    //       Add crimson to the color wheel --#17
    ColorWheel.addColor(Colors.Reds.Crimson);
    //       Add tomato to the color wheel --#18
    ColorWheel.addColor(Colors.Reds.Tomato);
    //      ------------- End of createColorPalette recipe --#6
  }
  private static void drawOctagonWithOverlap()
  {
    //    ------------- Recipe for drawOctagonWithOverlap --#7
    //       Do the following 8 + 1 times --#3
    for (int i = 0; i < 9; i++)
    {
      //         Move the tortoise 110 pixels --#1
      Tortoise.move(110);
      //         Turn the tortoise 1/8th of 360 degrees to the right --#2
      Tortoise.turn(360.0 / 8);
    }
    //         Repeat --#3
    //      ------------- End of drawOctagonWithOverlap recipe --#7
  }
}
