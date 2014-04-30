package org.teachingkidsprogramming.recipes;

import org.teachingextensions.windows.MouseLeftClickListener;

public class SimpleBubble implements MouseLeftClickListener
{
  public static void main(String[] args)
  {
    //Create a new SimpleBubble --#
    new SimpleBubble();
  }
  public SimpleBubble()
  {
    //Create a new ProgramWindow (field) --#
    //Listen for when the left mouse button is clicked --#
    //prepareColorPalette (recipe below) --#
    //------------- Recipe for PrepareColorPalette --#
    // Add alice blue to the color wheel --#
    // Add blue to the color wheel --#
    // Add dark blue to the color wheel --#
    // Add purple to the color wheel --#
    //------------- End of PrepareColorPalette recipe --#
  }
  @Override
  public void onLeftMouseClick(int x, int y)
  {
    // createBubble (recipe below) --#
    //------------- Recipe for CreateBubble --#
    // Remove previous bubbles --#
    // Change the color for the next circle to the next color from the color wheel --#
    // Set the radius for the circle to a random number between 10 and 50 --#
    // Create the bubble --#
    // Move the center of the bubble to the current position of the mouse on the window --#
    // Add the circle to the programWindow
    //------------- End of CreateBubble recipe --#
  }
}
