package org.teachingextensions.logo;

import java.awt.Color;

/**
 * ColorWheel is a place to store a color palette. <br/>
 * <b>Example:</b> If you have a palette of 
 * <font color="blue">blue</font>, 
 * <font color="red">red</font>, 
 * and <font color="purple">purple</font> <br/>
 * and then used it to print out the numbers, you would get <br/>
 * <font color="blue">1</font> 
 * <font color="red">2</font> 
 * <font color="purple">3</font>
 * <font color="blue">4</font> 
 * <font color="red">5</font> 
 * <font color="purple">6</font>
 * <font color="blue">7</font> 
 * <font color="red">8</font> 
 * <font color="purple">9</font>
 */
public class ColorWheel
{
  private static Wheel<Color> wheel = new Wheel<Color>();
  /**
   * This method adds a color to the ColorWheel. <br/>
   * <b>Example:</b> {@code  ColorWheel.addColor(Colors.Reds.Red);}
   * 
   * @param color
   *            the color to add to the wheel
   */
  public static void addColor(Color color)
  {
    wheel.add(color);
  }
  /**
   * This method returns the next color of the ColorWheel. <br/>
   * <b>Example:</b> {@code  Color penColor = ColorWheel.getNextColor();}
   * 
   * @return the next color of the ColorWheel
   */
  public static Color getNextColor()
  {
    return wheel.next();
  }
  public static void removeAllColors()
  {
    wheel.empty();
  }
}
