package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.logo.ColorWheel;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.shapes.Circle;
import org.teachingextensions.windows.MouseLeftClickListener;
import org.teachingextensions.windows.ProgramWindow;

import com.spun.util.NumberUtils;

public class SimpleBubble implements MouseLeftClickListener
{
  private ProgramWindow programWindow;
  public static void main(String[] args)
  {
    //Create a new SimpleBubble --#3
    new SimpleBubble();
  }
  public SimpleBubble()
  {
    //Create a new ProgramWindow (field) --#3
    programWindow = new ProgramWindow("Bubbles");
    //Set the CreateBubble recipe (below) to be called when the mouse is clicked --#3
    programWindow.addMouseLeftClickListener(this);
    //prepareColorPalette (recipe below) --#10
    prepareColorPalette();
  }
  public void prepareColorPalette()
  {
    //------------- Recipe for PrepareColorPalette --#9
    // Add alice blue to the color wheel --#5
    ColorWheel.addColor(Colors.Blues.AliceBlue);
    // Add blue to the color wheel --#6
    ColorWheel.addColor(Colors.Blues.Blue);
    // Add dark blue to the color wheel --#7
    ColorWheel.addColor(Colors.Blues.DarkBlue);
    // Add purple to the color wheel --#8
    ColorWheel.addColor(Colors.Purples.Purple);
    //------------- End of PrepareColorPalette recipe --#9
  }
  @Override
  public void onLeftMouseClick(int x, int y)
  {
    // createBubble (recipe below) --#
    createBubble(x, y);
  }
  public void createBubble(int x, int y)
  {
    //------------- Recipe for CreateBubble --#2
    // Remove previous bubbles --#11
    programWindow.removeAdditional();
    // Change the color for the next circle to the next color from the color wheel --#4
    // Set the radius for the circle to a random number between 10 and 50 --#1.2
    int radius = NumberUtils.getRandomInt(10, 50);
    // Create the bubble --#1.1
    Circle circle = new Circle(radius, ColorWheel.getNextColor());
    // Move the center of the bubble to the current position of the mouse on the window --#1.0 
    circle.setCenter(x, y);
    // Add the circle to the programWindow
    circle.addTo(programWindow);
    //------------- End of CreateBubble recipe --#2
  }
}
