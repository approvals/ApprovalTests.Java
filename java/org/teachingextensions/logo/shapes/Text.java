package org.teachingextensions.logo.shapes;

import java.awt.Graphics2D;

import org.teachingextensions.logo.Paintable;
import org.teachingextensions.logo.TurtlePanel;

public class Text implements Paintable
{
  private final String string;
  private int          x;
  private int          y;
  public Text(String string)
  {
    this.string = string;
  }
  public Text setTopLeft(int x, int y)
  {
    this.x = x;
    this.y = y;
    return this;
  }
  public void addTo(TurtlePanel panel)
  {
    panel.addAdditional(this);
  }
  @Override
  public void paint(Graphics2D g)
  {
    g.drawString(string, x, y);
  }
}
