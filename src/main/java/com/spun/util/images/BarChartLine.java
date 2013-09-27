package com.spun.util.images;

import java.awt.Color;
import java.util.Vector;

/**
  * An Interface for graphic objects
  **/
public class BarChartLine
{
  private Color color = null;
  private double value = 0;
  private String title = null;
  private String valueString = null;
  
  /***********************************************************************/
  /**
    * 
    **/
  public BarChartLine(double value, String title, String valueString)
  {
    this.color = Color.red;
    this.value = value;
    this.title = title;
    this.valueString = valueString;
  }
  /***********************************************************************/
  /**
    * 
    **/
  public Color getColor()
  {
    return this.color;
  }
  /***********************************************************************/
  /**
    * 
    **/
  public void setColor(Color color)
  {
    this.color = color;
  }
  /***********************************************************************/
  /**
    *
    **/
  public double getValue()
  {
    return this.value;
  }
  /***********************************************************************/
  /**
    *
    **/
  public String getTitle()
  {
    return this.title;
  }
  /***********************************************************************/
  /**
    *
    **/
  public String getValueString()
  {
    return this.valueString;
  }
  /***********************************************************************/
  /**
    *
    **/
  public int getScaledValue(double scale)
  {
    return (int)(this.value * scale);
  }
  /************************************************************************/
  /**
    * A convenience function to turn a vector of BarChartLine objects
    * into an Array of the same objects.
    * @param vectorOBarChartLine a Vector of BarChartLine objects
    * @return the array of objects.
    * @throws Error if an element of vectorOfBarChartLine is not a BarChartLine object.
    **/
  public static BarChartLine[] toArray(Vector vectorOfBarChartLine)
  {
    if (vectorOfBarChartLine == null) {return new BarChartLine[0];}
    BarChartLine array[] = new BarChartLine[vectorOfBarChartLine.size()];
    for(int i = 0; i <array.length; i++)
    {
      java.lang.Object rowObject = vectorOfBarChartLine.elementAt(i);
      if (rowObject instanceof BarChartLine)
      {
        array[i] = (BarChartLine)rowObject;
      }
      else
      {
        throw new Error("toArray[i] is not an instance of BarChartLine but a " + rowObject.getClass().getName());
      }
    }
    return array;
  }
  /***********************************************************************/
  /***********************************************************************/
}