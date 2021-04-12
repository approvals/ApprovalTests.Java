package com.spun.swing;

import java.awt.Dimension;
import java.awt.Graphics;

public interface Paintable
{
  Dimension getSize();
  void paint(Graphics g);
}
