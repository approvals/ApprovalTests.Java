package org.teachingextensions.logo;

import java.awt.Shape;
import java.awt.Stroke;

public class CompositeStroke implements Stroke
{
  private Stroke stroke1, stroke2;
  public CompositeStroke(Stroke stroke1, Stroke stroke2)
  {
    this.stroke1 = stroke1;
    this.stroke2 = stroke2;
  }
  public Shape createStrokedShape(Shape shape)
  {
    return stroke2.createStrokedShape(stroke1.createStrokedShape(shape));
  }
}
