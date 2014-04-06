package org.teachingextensions.logo.shapes;

import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JPanel;

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
  public void paint(Graphics2D g, JPanel caller)
  {
    Font font = g.getFont();
    Font font2 = new Font(font.getName(), font.getStyle() | Font.BOLD, font.getSize());
    g.setFont(font2);
    g.drawString(string, x, y);
  }
}
