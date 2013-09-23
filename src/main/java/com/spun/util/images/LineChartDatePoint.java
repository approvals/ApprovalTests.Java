package com.spun.util.images;

import java.awt.Color;
import java.util.Date;

/**
  * An Interface for graphic objects
  **/
public class LineChartDatePoint
  extends LineChartPoint
{
  /***********************************************************************/
  /**
    * 
    **/
  public LineChartDatePoint(Date x, double y)
  {
    super((double)x.getTime(),y);
  }
  /***********************************************************************/
  /**
    * 
    **/
  public LineChartDatePoint(Date x, double y, Color flagColor)
  {
    super((double)x.getTime(), y, flagColor);
  }
  /***********************************************************************/
  /***********************************************************************/
}