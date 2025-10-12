package org.approvaltests.awt;

import com.spun.swing.Paintable;
import com.spun.swing.SwingUtils;
import com.spun.util.WindowUtils;
import org.lambda.actions.Action0;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class CustomPanel implements Paintable
{
  public static void main(String[] args)
  {
    WindowUtils.testPaintable(new CustomPanel());
  }
  private int     length = 10;
  private boolean showCoordinated;
  public CustomPanel()
  {
  }

  public CustomPanel(boolean showCoordinated, int length)
  {
    this.showCoordinated = showCoordinated;
    this.length = length;
  }

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
    for (int y1 = 0; y1 < length; y1++)
    {
      for (int x1 = 0; x1 < length; x1++)
      {
        g.setColor(square++ % 2 == 0 ? Color.BLACK : Color.white);
        g.fillRect((x1 * length), (y1 * length), length, length);
        if (showCoordinated)
        {
          g.setFont(new Font("Helvetica", Font.PLAIN, 8));
          g.setColor(square % 2 == 0 ? Color.BLACK : Color.white);
          SwingUtils.drawCenteredString(g, x1 + "," + y1, x1 * length + (length / 2), y1 * length + (length / 2));
        }
      }
      square++;
    }
  }

  @Override
  public void registerRepaint(Action0 repaint)
  {
  }
}
