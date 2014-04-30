package org.teachingextensions.logo;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;

import org.teachingextensions.logo.Turtle.Animals;
import org.teachingextensions.windows.ProgramWindow;

public class TurtlePanel extends ProgramWindow
{
  private Turtle turtle;
  private Image  image;
  public TurtlePanel()
  {
  }
  public void setTurtle(Turtle turtle)
  {
    this.turtle = turtle;
  }
  @Override
  public void paint(Graphics g)
  {
    super.paint(g);
    paintLines((Graphics2D) g);
    paintTurtle((Graphics2D) g);
  }
  private void paintLines(Graphics2D g)
  {
    if (turtle == null) { return; }
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    for (LineSegment l : turtle.getTrail())
    {
      if (l != null)
      {
        g.setColor(l.getColor());
        g.setStroke(new BasicStroke(l.getWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        g.draw(new Line2D.Double(l.getStart().x, l.getStart().y, l.getEnd().x, l.getEnd().y));
      }
    }
  }
  private void paintTurtle(Graphics2D g)
  {
    if (turtle == null || turtle.isHidden()) { return; }
    Image image = getImage();
    int xCenter = image.getWidth(null) / 2;
    int yCenter = image.getHeight(null) / 2;
    int x = turtle.getX() - xCenter;
    int y = turtle.getY() - yCenter;
    AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(turtle.getHeadingInDegrees()),
        xCenter, yCenter);
    AffineTransform move = AffineTransform.getTranslateInstance(x, y);
    move.concatenate(rotate);
    g.drawImage(image, move, null);
  }
  public synchronized Image getImage()
  {
    if (image == null)
    {
      setAnimal(Animals.Turtle);
    }
    return image;
  }
  public synchronized void setAnimal(Animals animal)
  {
    image = new ImageIcon(this.getClass().getResource(animal + ".png")).getImage();
  }
}
