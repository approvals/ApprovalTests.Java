package com.spun.swing;

import java.awt.Graphics;
import javax.swing.JPanel;

class PaintablePanel extends JPanel
{
  private final Paintable paintable;
  public PaintablePanel(Paintable paintable)
  {
    this.paintable = paintable;
    setPreferredSize(paintable.getSize());
  }
  @Override
  public void paint(Graphics g)
  {
    paintable.paint(g);
  }
}
