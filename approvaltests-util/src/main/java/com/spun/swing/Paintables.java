package com.spun.swing;

import javax.swing.JPanel;

public class Paintables
{
  public static JPanel asPanel(Paintable paintable)
  {
    return new PaintablePanel(paintable);
  }
}
