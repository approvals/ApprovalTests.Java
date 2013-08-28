package org.teachingkidsprogramming.recipes;

import org.teachingextensions.windows.MouseLeftClickListener;
import org.teachingextensions.windows.MouseRightClickListener;

public class Dots implements MouseRightClickListener, MouseLeftClickListener
{
  public static void main(String[] args)
  {
    //Create a Dots window.
  }
  public Dots()
  {
    //  ------------- Recipe for Dots
    // Listen for right clicks on the window for the tortoise 
    // Listen for left clicks on the window for the tortoise 
    //Make the Tortoise go as fast as possible.
    //  clearTheScreen (recipe below)
    //  ------------- Recipe for clearTheScreen
    //   Clear the Tortoise
    //   Write "Right click to clear" on the screen at position 100, 100
    //  ------------- End of clearTheScreen Recipe
    //  prepareColorPalette (recipe below)
    //  ------------- Recipe for prepareColorPalette
    //   Add red to the color wheel
    //   Add green to the color wheel
    //   Add blue to the color wheel
    //   Add purple to the color wheel
    //   Add pink to the color wheel
    //   Add teal to the color wheel
    //  ------------- End of prepareColorPalette Recipe
    //  ------------- End of Dots Recipe
  }
  @Override
  public void onRightMouseClick(int x, int y)
  {
    //  clearTheScreen (recipe below)
  }
  @Override
  public void onLeftMouseClick(int x, int y)
  {
    //  connectTheDots (recipe below)
    //  ------------- Recipe for connectTheDots
    //   addACircle (recipe below)
    //  ------------- Recipe for addACircle
    //   Create a circle with a radius of 7 which is the same color as the next color on the colorwheel
    //   Change the circle to be 40% opaque
    //   Move the circle so that it's center is at the current position of the mouse
    //Place the circle on the tortoise's window.
    //  ------------- End of addACircle Recipe
    //   Move the tortoise to the current position of the mouse # 8
    //  ------------- End of connectTheDots Recipe
  }
}
