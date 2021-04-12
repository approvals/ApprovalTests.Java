package com.spun.swing;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Paintables
{
  public static JPanel asPanel(Paintable paintable)
  {
    return new JPanel()
    {
      {
        setPreferredSize(paintable.getSize());
      }
      @Override
      public void paint(Graphics g)
      {
        paintable.paint(g);
      }
    };
  }
}
