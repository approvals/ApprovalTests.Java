package com.spun.swing;

import javax.swing.JPanel;
import java.awt.Graphics;

public class PaintablePanel<P extends Paintable> extends JPanel
{
  private final P paintable;
  public PaintablePanel(P paintable)
  {
    this.paintable = paintable;
    setPreferredSize(paintable.getSize());
    paintable.registerRepaint(() -> this.repaint());
  }

  @Override
  public void paint(Graphics g)
  {
    paintable.paint(g);
  }

  public P get()
  {
    return paintable;
  }
}
