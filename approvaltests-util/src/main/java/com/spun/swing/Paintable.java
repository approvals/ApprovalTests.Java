package com.spun.swing;

import org.lambda.actions.Action0;

import java.awt.Dimension;
import java.awt.Graphics;

public interface Paintable
{
  Dimension getSize();

  void paint(Graphics g);

  void registerRepaint(Action0 repaint);
}
