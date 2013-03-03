package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.logo.ColorWheel;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.utils.TortoiseUtils;

public class Dots
{
  public static void main(String[] args)
  {
    //    Set the ClickMouse recipe (below) to be called when the mouse is clicked
    clickMouse();
    //    ClearTheScreen (recipe below)
    clearTheScreen();
    //    PrepareColorPalette (recipe below)
    prepareColorPalette();
  }
  //    ------------- Recipe for ClickMouse
  private static void clickMouse()
  {
    //     Play a chime
    //
    //     If the right mouse button is clicked, then
    //
    //     ClearTheScreen (recipe below)
    clearTheScreen();
    //     Otherwise
    //
    //     ConnectTheDots (recipe below)
    connectTheDots();
  }
  //    ------------- Recipe for ConnectTheDots
  private static void connectTheDots()
  {
    //     AddACircle (recipe below)
    addACircle();
    //     Move the tortoise to the current position of the mouse
    //
  }
  //    ------------- Recipe for AddACircle
  private static void addACircle()
  {
    //     The width of the circle is 15
    int width = 15;
    //     Change the color for the next shape to the next color from the color wheel
    //
    //     Create a circle
    //
    //     Change the circle to be 40% opaque
    //
    //     Move the center of the circle to the current position of the mouse
  }
  //    ------------- Recipe for PrepareColorPalette
  private static void prepareColorPalette()
  {
    //     Add red to the color wheel
    ColorWheel.addColor(Colors.Reds.Red);
    //     Add green to the color wheel
    ColorWheel.addColor(Colors.Greens.Green);
    //     Add blue to the color wheel
    ColorWheel.addColor(Colors.Blues.Blue);
    //     Add purple to the color wheel
    ColorWheel.addColor(Colors.Purples.Purple);
    //     Add pink to the color wheel
    ColorWheel.addColor(Colors.Pinks.Pink);
    //     Add teal to the color wheel
    ColorWheel.addColor(Colors.Blues.Teal);
  }
  //    ------------- Recipe for ClearTheScreen
  private static void clearTheScreen()
  {
    //     Clear the Program Window
    Tortoise.cleanUpWindow();
    //     Write "Right click to clear" on the screen at position 100, 100
    TortoiseUtils.writeMessage("Right click to clear", 100, 100);
  }
}
