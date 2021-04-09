package org.approvaltests.machine_specific_tests;

import org.lambda.functions.Function2;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel
{
  public CustomPanel()
  {
    setPreferredSize(new Dimension(100, 100));
    setSize(getPreferredSize());
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
