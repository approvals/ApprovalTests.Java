package com.spun.swing;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class SwingUtils
{
  public static void drawCenteredString(Graphics g, String text, int x, int y)
  {
    FontMetrics metrics = g.getFontMetrics();
    int width = metrics.stringWidth(text);
    x -= width / 2;
    int height = metrics.getHeight();
    y -= height / 2;
    y += metrics.getAscent();
    g.drawString(text, x, y);
  }
}
