package com.spun.swing;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class SwingUtils
{
  public static void drawCenteredString(Graphics g, String text, int x, int y)
  {
    FontMetrics metrics = g.getFontMetrics();
    x -= (metrics.stringWidth(text) / 2);
    y -= (metrics.getHeight() / 2);
    y += metrics.getAscent();
    g.drawString(text, x, y);
  }
}
