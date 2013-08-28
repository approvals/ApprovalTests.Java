package org.teachingkidsprogramming.recipes;

import org.teachingextensions.windows.MouseLeftClickListener;
import org.teachingextensions.windows.MouseRightClickListener;

public class Dots implements MouseRightClickListener, MouseLeftClickListener
{
  public static void main(String[] args)
  {
    //Create a Dots window. --#1.1
  }
  public Dots()
  {
    // Listen for right clicks on the window for the tortoise  --#20.2
    // Listen for left clicks on the window for the tortoise  --#1.2
    //Make the Tortoise go as fast as possible. --#4
    //  clearTheScreen (recipe below) --#19
    //  ------------- Recipe for clearTheScreen --#19
    //   Clear the Tortoise --#20.1
    //   Write "Right click to clear" on the screen at position 100, 100 --#18
    //  ------------- End of clearTheScreen Recipe --#19
    //  prepareColorPalette (recipe below) --#17
    //  ------------- Recipe for prepareColorPalette --#17
    //   Add red to the color wheel --#6
    //   Add green to the color wheel --#12
    //   Add blue to the color wheel --#13
    //   Add purple to the color wheel --#14
    //   Add pink to the color wheel --#15
    //   Add teal to the color wheel --#16
    //  ------------- End of prepareColorPalette Recipe --#17
  }
  @Override
  public void onRightMouseClick(int x, int y)
  {
    //  clearTheScreen (recipe below) --#20.3
  }
  @Override
  public void onLeftMouseClick(int x, int y)
  {
    //  connectTheDots (recipe below) --#11
    //  ------------- Recipe for connectTheDots --#11
    //   addACircle (recipe below) --#10
    //  ------------- Recipe for addACircle --#10
    //   Create a circle with a radius of 7 which is the same color as the next color on the colorwheel --#5
    //   Change the circle to be 40% opaque --#9
    //   Move the circle so that it's center is at the current position of the mouse --#8
    //Place the circle on the tortoise's window. --#7
    //  ------------- End of addACircle Recipe --#10
    //   Move the tortoise to the current position of the mouse --#2
    //  ------------- End of connectTheDots Recipe --#11
  }
}
