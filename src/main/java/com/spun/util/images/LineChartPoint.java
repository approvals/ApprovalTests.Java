package com.spun.util.images;

import java.awt.Color;
import java.util.Vector;
import com.objectplanet.chart.*;

/**
  * An Interface for graphic objects
  **/
public class LineChartPoint
{
  private double x = 0;
  private double y = 0;
  private String label = null;
  private Color flagColor = null;
  
  /***********************************************************************/
  /**
    * 
    **/
  public LineChartPoint(double x, double y)
  {
    this.x = x;
    this.y = y;
  }
  /***********************************************************************/
  /**
    * 
    **/
  public LineChartPoint(double x, double y, String label)
  {
    this(x,y);
    this.label = label;
  }
  /***********************************************************************/
  /**
    * 
    **/
  public LineChartPoint(double x, double y, Color flagColor)
  {
    this(x,y);
    this.flagColor = flagColor;
  }
  /***********************************************************************/
  /**
    * 
    **/
  public LineChartPoint(double x, double y, String label, Color flagColor)
  {
    this(x,y, flagColor);
    this.label = label;
  }
  /***********************************************************************/
  public double getX()
  {
    return this.x;
  }
  /***********************************************************************/
  public double getY()
  {
    return this.y;
  }
  /***********************************************************************/
  public String getLabel()
  {
    return this.label;
  }
  /***********************************************************************/
  public void setLabel(String label)
  {
    this.label = label;
  }
  /***********************************************************************/
  public boolean isFlagged()
  {
    return this.flagColor != null;
  }
  /************************************************************************/
  public ChartSample getChartSample(int index)
  {
    ChartSample s = new ChartSample(index,y);
    s.setLabel(label);
    
    return s;
  }
  /***********************************************************************/
  public Color getFlagColor()
  {
    return this.flagColor;
  }
  /************************************************************************/
  /**
    * A convenience function to turn a vector of BarChartLine objects
    * into an Array of the same objects.
    * @param vectorOBarChartLine a Vector of BarChartLine objects
    * @return the array of objects.
    * @throws Error if an element of vectorOfBarChartLine is not a BarChartLine object.
    **/
  public static LineChartPoint[] toArray(Vector vectorOfLineChartLine)
  {
    if (vectorOfLineChartLine == null) {return new LineChartPoint[0];}
    LineChartPoint array[] = new LineChartPoint[vectorOfLineChartLine.size()];
    for(int i = 0; i <array.length; i++)
    {
      java.lang.Object rowObject = vectorOfLineChartLine.elementAt(i);
      if (rowObject instanceof LineChartPoint)
      {
        array[i] = (LineChartPoint)rowObject;
      }
      else
      {
        throw new Error("toArray[i] is not an instance of LineChartPoint but a " + rowObject.getClass().getName());
      }
    }
    return array;
  }
  /***********************************************************************/
  /***********************************************************************/
}