package org.teachingextensions.logo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.teachingextensions.logo.Turtle.Animals;
import org.teachingextensions.windows.LeftClickMouseAdapter;
import org.teachingextensions.windows.MouseLeftClickListener;
import org.teachingextensions.windows.MouseRightClickListener;
import org.teachingextensions.windows.RightClickMouseAdapter;

public class TurtlePanel extends JPanel
{
  private Turtle               turtle;
  private ArrayList<Paintable> additional = new ArrayList<Paintable>();
  private Image                image;
  public TurtlePanel()
  {
    setPreferredSize(new Dimension(627, 442));
    setColor(Colors.Whites.White);
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
    for (Paintable p : additional)
    {
      p.paint((Graphics2D) g);
    }
  }
  private void paintLines(Graphics2D g)
  {
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
    if (turtle.isHidden()) { return; }
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
  public void setColor(Color backgroundColor)
  {
    setBackground(backgroundColor);
  }
  public void addAdditional(Paintable additional)
  {
    this.additional.add(additional);
    repaint();
  }
  public void removeAdditional()
  {
    additional.clear();
    repaint();
  }
  public void addMouseRightClickListener(MouseRightClickListener listener)
  {
    addMouseListener(new RightClickMouseAdapter(listener));
  }
  public void addMouseLeftClickListener(MouseLeftClickListener listener)
  {
    addMouseListener(new LeftClickMouseAdapter(listener));
  }
}
