package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.logo.ColorWheel;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.windows.GraphicsWindow;

public class SimpleBubble
{
  //  Hint: you will need to use the ProgramWindow & ShapeMaker objects.
  public static void main(String[] args)
  {
    //  show the GraphicsWindow
    GraphicsWindow.show();
    //  prepareColorPalette (recipe below) --#10
    prepareColorPalette();
    //  Set the CreateBubble recipe (below) to be called when the mouse is clicked --#3
    //    createBubble();
  }
  private static void prepareColorPalette()
  {
    //  ------------- Recipe for PrepareColorPalette --#9
    //   Add alice blue to the color wheel --#5
    ColorWheel.addColor(Colors.Blues.AliceBlue);
    //   Add blue to the color wheel --#6
    ColorWheel.addColor(Colors.Blues.Blue);
    //   Add dark blue to the color wheel --#7
    ColorWheel.addColor(Colors.Blues.DarkBlue);
    //   Add purple to the color wheel --#8
    ColorWheel.addColor(Colors.Purples.Purple);
    //  ------------- End of PrepareColorPalette recipe --#9
  }
  private static void createBubble()
  {
    //  ------------- Recipe for CreateBubble --#2
    //   Remove the current bubble --#11
    //
    //   Change the color for the next circle to the next color from the color wheel --#4
    //
    //   Set the radius for the circle to a random number between 10 and 50 --#1.2
    //
    //   Create the bubble --#1.1
    //
    //   Move the center of the bubble to the current position of the mouse on the window --#1.0 (Fake at 20,20) &#
    //
    //  ------------- End of CreateBubble recipe --#2
  }
}
