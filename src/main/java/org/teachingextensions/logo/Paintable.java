package org.teachingextensions.logo;

import java.awt.Graphics2D;

public interface Paintable
{
  public static Paintable NOTHING = new Paintable()
                                  {
                                    public void paint(Graphics2D g)
                                    {
                                    }
                                  };
  void paint(Graphics2D g);
}
