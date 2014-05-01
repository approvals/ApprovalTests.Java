package org.teachingkidsprogramming.section07events;

import org.teachingextensions.windows.MouseLeftClickListener;

public class SimpleBubble implements MouseLeftClickListener
{
  public SimpleBubble()
  {
    //Create a ProgramWindow titled Bubbles --#1
    //Have SimpleBubble listen for when the left mouse button is clicked in your program window --#2.2
    //prepareColorPalette (recipe below) --#7
    //------------- Recipe for prepareColorPalette --#7
    // Add purple to the color wheel --#2.3
    // Add alice blue to the color wheel --#4
    // Add blue to the color wheel --#5
    // Add dark blue to the color wheel --#6
    //------------- End of prepareColorPalette recipe --#7
  }
  @Override
  public void onLeftMouseClick(int x, int y)
  {
    // createBubble (recipe below) --#8
    //------------- Recipe for createBubble --#8
    // Remove previous bubbles from your program window --#9
    // Set the radius for the circle to a random number between 10 and 50 --#2.5
    // Create a circle with the radius and the next color from the color wheel --#2.1
    // Move the center of the bubble to the current position of the mouse on the window --#3
    // Add the circle to your program window --#2.4
    //------------- End of createBubble recipe --#8
  }
  public static void main(String[] args)
  {
    new SimpleBubble();
  }
}