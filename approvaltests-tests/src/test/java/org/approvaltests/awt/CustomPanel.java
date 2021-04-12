package org.approvaltests.awt;

import com.spun.swing.Paintable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class CustomPanel implements Paintable
{
  @Override
  public Dimension getSize()
  {
    return new Dimension(100, 100);
  }
  @Override
  public void paint(Graphics g)
  {
    int square = 0;
    StringBuffer b = new StringBuffer();
    for (int y1 = 0; y1 < 10; y1++)
    {
      for (int x1 = 0; x1 < 10; x1++)
      {
        g.setColor(square++ % 2 == 0 ? Color.BLACK : Color.white);
        g.fillRect((x1 * 10), (y1 * 10), 10, 10);
      }
      square++;
    }
  }
}
