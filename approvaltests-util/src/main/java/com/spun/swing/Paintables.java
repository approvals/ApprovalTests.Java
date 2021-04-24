package com.spun.swing;

import javax.swing.JPanel;

public class Paintables
{
  public static <P extends Paintable> PaintablePanel<P> asPanel(P paintable)
  {
    return new PaintablePanel(paintable);
  }
}
