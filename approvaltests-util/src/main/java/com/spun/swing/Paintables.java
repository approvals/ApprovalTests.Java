package com.spun.swing;

public class Paintables
{
  public static <P extends Paintable> PaintablePanel<P> asPanel(P paintable)
  {
    return new PaintablePanel<>(paintable);
  }
}
