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
  public SimpleBubble()
  {
    //Create a ProgramWindow titled Bubbles --#1
    programWindow = new ProgramWindow("Bubbles");
    //Have SimpleBubble listen for when the left mouse button is clicked in your program window --#2.2
    programWindow.addMouseLeftClickListener(this);
    //prepareColorPalette (recipe below) --#7
    prepareColorPalette();
  }
  private void prepareColorPalette()
  {
    //------------- Recipe for prepareColorPalette --#7
    // Add alice blue to the color wheel --#4
    ColorWheel.addColor(Colors.Blues.AliceBlue);
    // Add blue to the color wheel --#5
    ColorWheel.addColor(Colors.Blues.Blue);
    // Add dark blue to the color wheel --#6
    ColorWheel.addColor(Colors.Blues.DarkBlue);
    // Add purple to the color wheel --#2.3
    ColorWheel.addColor(Colors.Purples.Purple);
    //------------- End of prepareColorPalette recipe --#7
  }
  @Override
  public void onLeftMouseClick(int x, int y)
  {
    // createBubble (recipe below) --#8
    createBubble(x, y);
  }
  private void createBubble(int x, int y)
  {
    //------------- Recipe for createBubble --#8
    // Remove previous bubbles from your program window --#9
    programWindow.removePaintable();
    // Set the radius for the circle to a random number between 10 and 50 --#2.5
    int radius = NumberUtils.getRandomInt(10, 50);
    // Create a circle with the radius and the next color from the color wheel --#2.1
    Circle circle = new Circle(radius, ColorWheel.getNextColor());
    // Move the center of the bubble to the current position of the mouse on the window --#3
    circle.setCenter(x, y);
    // Add the circle to your program window --#2.4
    circle.addTo(programWindow);
    //------------- End of createBubble recipe --#8
  }
  public static void main(String[] args)
  {
    new SimpleBubble();
  }
}