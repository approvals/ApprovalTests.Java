package org.teachingkidsprogramming.recipes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.teachingextensions.logo.ColorWheel;
import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.shapes.Circle;

import com.spun.util.WindowUtils;

public class Dots implements MouseListener
{
  public static void main(String[] args)
  {
    // createWindow() #2
    Tortoise.show();
    Tortoise.setSpeed(10);
    //  Set the clickMouse recipe (below) to be called when the mouse is clicked #3
    Tortoise.getBackgroundWindow().addMouseListener(new Dots());
    //  clearTheScreen (recipe below)
    //
    //  prepareColorPalette (recipe below)
    prepareColorPalette();
    //
    //
    //
    //  ------------- Recipe for clearTheScreen
    //   Clear the Program Window
    //
    //   Write "Right click to clear" on the screen at position 100, 100
    //
    //
  }
  private static void prepareColorPalette()
  { //  ------------- Recipe for prepareColorPalette
    //
    //   Add red to the color wheel
    ColorWheel.addColor(Colors.Reds.Red);
    //   Add green to the color wheel
    ColorWheel.addColor(Colors.Greens.Green);
    //   Add blue to the color wheel
    ColorWheel.addColor(Colors.Blues.Blue);
    //   Add purple to the color wheel
    ColorWheel.addColor(Colors.Purples.Purple);
    //   Add pink to the color wheel
    ColorWheel.addColor(Colors.Pinks.Pink);
    //   Add teal to the color wheel
    ColorWheel.addColor(Colors.Greens.Teal);
  }
  public static JPanel createWindow()
  {
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(640, 480));
    WindowUtils.testPanel(panel);
    return panel;
  }
  @Override
  public void mouseClicked(MouseEvent m)
  {
    //    ------- Recipe for clickMouse #4
    //   Play a chime #1
    System.out.println("clicked");
    //   If the right mouse button is clicked, then #5
    if (SwingUtilities.isRightMouseButton(m))
    {
      //      clearTheScreen() #6
      clearTheScreen();
    }
    //    Otherwise
    else
    {
      //      connectTheDots()
      connectTheDots(m.getX(), m.getY());
    }
  }
  private void connectTheDots(int x, int y)
  {
    //  ------------- Recipe for connectTheDots
    //   addACircle (recipe below)
    addCircle(x, y);
    //   Move the tortoise to the current position of the mouse # 8
    Tortoise.moveTo(x, y);
  }
  private void addCircle(int x, int y)
  {
    //  ------------- Recipe for addACircle
    //   The width of the circle is 15
    int radius = 7;
    //   Change the color for the next shape to the next color from the color wheel
    Color color = ColorWheel.getNextColor();
    //   Create a circle
    Circle circle = new Circle(radius, color);
    //   Change the circle to be 40% opaque
    circle.setTransparency(60);
    //   Move the center of the circle to the current position of the mouse
    circle.setCenter(x, y);
    circle.addTo(Tortoise.getBackgroundWindow());
  }
  private void clearTheScreen()
  {
  }
  @Override
  public void mouseEntered(MouseEvent arg0)
  {
    //    System.out.println("entered");
  }
  @Override
  public void mouseExited(MouseEvent arg0)
  {
    //    System.out.println("exited");
  }
  @Override
  public void mousePressed(MouseEvent arg0)
  {
    //    System.out.println("pressed");
  }
  @Override
  public void mouseReleased(MouseEvent arg0)
  {
    //    System.out.println("released");
  }
}
