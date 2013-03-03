package com.spun.util.images;

import java.awt.Color;
import java.util.Vector;

import com.objectplanet.chart.*;


/**
  * An Interface for graphic objects
  **/
public class LineChartLine
{
  private Color color = null;
  private Vector values = new Vector();
  private String title = null;
  private String valueString = null;
  
  /***********************************************************************/
  /**
    * 
    **/
  public LineChartLine(String title, String valueString)
  {
    this.color = Color.red;
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
  public LineChartPoint[] getValues()
  {
    return LineChartPoint.toArray(values);
  }
  /***********************************************************************/
  /**
    *
    **/
  public void addPoint(LineChartPoint point)
  {
    boolean inserted = false;
    
    for (int i = 0; i < values.size(); i++)
    {
      LineChartPoint p = (LineChartPoint) values.get(i);
      
      if (p.getX() > point.getX())
      {
        values.insertElementAt(point,i);
        inserted = true;
        break;
      }
    }
    
    if (!inserted)
    {
      values.add(point);
    }
  }
  /***********************************************************************/
  /**
    *
    **/
  public int getSize()
  {
    return values.size();
  }
  /***********************************************************************/
  /**
    *
    **/
  public ChartSample [] getChartSamples()
  {
    LineChartPoint [] points = getValues();
    
    ChartSample [] samples = new ChartSample[points.length];
    
    for (int i = 0; i < points.length; i++)
    {
      samples[i] = points[i].getChartSample(i);
//      My_System.variable("chartSample = " + samples[i]);
    }
    
    return samples;
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
    * Does this line have any flagged points?
    **/
  public boolean hasFlags()
  {
    for (int i = 0; i < values.size(); i++)
    {
      LineChartPoint p = (LineChartPoint) values.get(i);
    
      if (p.isFlagged())
      {
        return true;
      }
    }
    
    return false;
  }
  /************************************************************************/
  /**
    * A convenience function to turn a vector of BarChartLine objects
    * into an Array of the same objects.
    * @param vectorOBarChartLine a Vector of BarChartLine objects
    * @return the array of objects.
    * @throws Error if an element of vectorOfBarChartLine is not a BarChartLine object.
    **/
  public static LineChartLine[] toArray(Vector vectorOfLineChartLine)
  {
    if (vectorOfLineChartLine == null) {return new LineChartLine[0];}
    LineChartLine array[] = new LineChartLine[vectorOfLineChartLine.size()];
    for(int i = 0; i <array.length; i++)
    {
      java.lang.Object rowObject = vectorOfLineChartLine.elementAt(i);
      if (rowObject instanceof LineChartLine)
      {
        array[i] = (LineChartLine)rowObject;
      }
      else
      {
        throw new Error("toArray[i] is not an instance of LineChartLine but a " + rowObject.getClass().getName());
      }
    }
    return array;
  }
  /***********************************************************************/
  /***********************************************************************/
}