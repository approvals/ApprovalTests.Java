package org.teachingextensions.logo;

import java.awt.Color;
import java.awt.Point;

public class LineSegment
{
  private Color     color;
  private Point     start;
  private Point     end;
  private final int width;
  public LineSegment(Color color, Point start, Point end, int width)
  {
    super();
    this.color = color;
    this.start = start;
    this.end = end;
    this.width = width;
  }
  public Color getColor()
  {
    return color;
  }
  public Point getStart()
  {
    return start;
  }
  public Point getEnd()
  {
    return end;
  }
  public float getWidth()
  {
    return width;
  }
}
