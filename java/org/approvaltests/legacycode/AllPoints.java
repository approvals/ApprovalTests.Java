package org.approvaltests.legacycode;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class AllPoints
{
  public static class MyPoint extends Point
  {
    public MyPoint(int x, int y)
    {
      super(x, y);
    }
    @Override
    public String toString()
    {
      return String.format("[%s,%s]", x, y);
    }
  }
  public static Point[] get(int xStart, int yStart, int xEnd, int yEnd)
  {
    int xLength = xEnd - xStart + 1;
    int yLength = yEnd - yStart + 1;
    List<Point> points = new ArrayList<Point>(xLength * yLength);
    for (int x = xStart; x <= xEnd; x++)
    {
      for (int y = yStart; y <= yEnd; y++)
      {
        points.add(new MyPoint(x, y));
      }
    }
    return points.toArray(new Point[points.size()]);
  }
}
