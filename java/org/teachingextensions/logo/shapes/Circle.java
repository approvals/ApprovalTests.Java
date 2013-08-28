package org.teachingextensions.logo.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import org.teachingextensions.logo.Paintable;
import org.teachingextensions.logo.TurtlePanel;

public class Circle implements Paintable
{
  private final int   radius;
  private final Color color;
  private int         x;
  private int         y;
  public Circle(int radius, Color color)
  {
    this.radius = radius;
    this.color = color;
  }
  public void setCenter(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
  public void addTo(TurtlePanel panel)
  {
    panel.addAdditional(this);
  }
  @Override
  public void paint(Graphics2D g)
  {
    g.setColor(color);
    g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
  }
}
