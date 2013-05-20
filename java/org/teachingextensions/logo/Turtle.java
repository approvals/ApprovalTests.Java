package org.teachingextensions.logo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.approvaltests.writers.ComponentApprovalWriter;

import com.spun.util.WindowUtils;
import com.spun.util.persistence.Saver;
import com.spun.util.persistence.SavingException;

public class Turtle
{
  /**
   * Current types are: Turtle, Spider
   * 
   */
  public enum Animals {
    Turtle, Spider
  }
  private class Turner implements Saver<Double>
  {
    @Override
    public Double save(Double save) throws SavingException
    {
      smallTurn(save);
      return save;
    }
  }
  private class Mover implements Saver<Double>
  {
    private final Point starting;
    private LineSegment line = null;
    public Mover(Point point)
    {
      this.starting = point;
    }
    @Override
    public Double save(Double save) throws SavingException
    {
      moveWithoutAnimation(save);
      if (line != null)
      {
        trail.remove(line);
      }
      line = new LineSegment(color, starting, new Point(getX(), getY()), width);
      trail.add(line);
      return save;
    }
  }
  private class EmptyMover implements Saver<Double>
  {
    @Override
    public Double save(Double save) throws SavingException
    {
      moveWithoutAnimation(save);
      return save;
    }
  }
  private static final double MAX_MOVE_AMOUNT = 5.0;
  public static final int     TEST_SPEED      = Integer.MIN_VALUE;
  private static double       MAX_TURN_AMOUNT = 5.0;
  private double              x               = 640 / 2;
  private double              y               = 480 / 2;
  private double              angleInDegrees  = 0;
  private TurtlePanel         panel;
  private JFrame              frame;
  private int                 speed           = 1;
  private List<LineSegment>   trail           = new ArrayList<LineSegment>();
  private Color               color           = Color.black;
  private int                 width           = 2;
  private boolean             penDown         = true;
  private boolean             hidden;
  public BufferedImage getImage()
  {
    BufferedImage image = ComponentApprovalWriter.drawComponent(getPanel());
    cleanUpWindow();
    return image;
  }
  public void cleanUpWindow()
  {
    if (frame != null)
    {
      frame.setVisible(false);
      frame = null;
    }
    panel = null;
  }
  private Component getPanel()
  {
    if (panel == null)
    {
      panel = new TurtlePanel();
      panel.setTurtle(this);
      if (speed != TEST_SPEED)
      {
        frame = new JFrame("Turtle");
        frame.getContentPane().add(panel);
        WindowUtils.testFrame(frame, true);
      }
    }
    return panel;
  }
  public int getX()
  {
    return (int) x;
  }
  public int getY()
  {
    return (int) y;
  }
  public double getAngleInDegrees()
  {
    return angleInDegrees;
  }
  public void setAngleInDegrees(double angleInDegrees)
  {
    this.angleInDegrees = angleInDegrees;
  }
  public void setX(Number x)
  {
    this.x = x.doubleValue();
  }
  public void setY(Number y)
  {
    this.y = y.doubleValue();
  }
  public void turn(double amount)
  {
    double max = getTurnAmount(amount);
    Saver<Double> s = new Turner();
    animate(amount, max, s);
  }
  private double getTurnAmount(double amount)
  {
    amount = Math.abs(amount);
    if (getSpeed() == TEST_SPEED) { return amount; }
    return amount / (11 - getSpeed());
  }
  private void animate(double amount, double max, Saver<Double> s)
  {
    double sign = amount > 0 ? 1 : -1;
    amount = Math.abs(amount);
    while (amount > max)
    {
      s.save(max * sign);
      refreshPanel();
      amount -= max;
    }
    s.save(amount * sign);
    refreshPanel();
  }
  private void refreshPanel()
  {
    long delay = getDelay();
    if (delay != TEST_SPEED)
    {
      getPanel().repaint();
      try
      {
        Thread.sleep(delay);
      }
      catch (InterruptedException e)
      {
        // do nothing
      }
    }
  }
  private void smallTurn(double i)
  {
    angleInDegrees += i;
  }
  private long getDelay()
  {
    if (getSpeed() == 10) { return 1; }
    if (getSpeed() == TEST_SPEED) { return TEST_SPEED; }
    return 100 / getSpeed();
  }
  public void setSpeed(int speed)
  {
    if (speed != TEST_SPEED)
    {
      if (speed < 1 || 10 < speed) { throw new RuntimeException(
          String
              .format(
                  "I call shenanigans!!!\nThe speed '%s' is not between the acceptable range of [1-10]\nPerhaps you should read the documentation",
                  speed)); }
    }
    this.speed = speed;
  }
  public int getSpeed()
  {
    return speed;
  }
  public double getHeadingInDegrees()
  {
    return angleInDegrees;
  }
  public void move(Number amount)
  {
    double max = MAX_MOVE_AMOUNT;
    Saver<Double> s = penDown ? new Mover(new Point(getX(), getY())) : new EmptyMover();
    animate(amount.doubleValue(), max, s);
  }
  private void moveWithoutAnimation(Double save)
  {
    x += getDeltaX(save, angleInDegrees);
    y += getDeltaY(save, angleInDegrees);
  }
  public static double getDeltaY(double i, double angleInDegrees2)
  {
    return -i * Math.cos(Math.toRadians(angleInDegrees2));
  }
  public static double getDeltaX(double i, double angleInDegrees2)
  {
    return i * Math.sin(Math.toRadians(angleInDegrees2));
  }
  public LineSegment[] getTrail()
  {
    return trail.toArray(new LineSegment[0]);
  }
  public void setPenColor(Color color)
  {
    this.color = color;
  }
  public Color getPenColor()
  {
    return color;
  }
  public int getPenWidth()
  {
    return width;
  }
  public void setPenWidth(int width)
  {
    this.width = width;
  }
  public void show()
  {
    hidden = false;
    refreshPanel();
  }
  public TurtlePanel getBackgroundWindow()
  {
    return (TurtlePanel) getPanel();
  }
  public void setAnimal(Animals animal)
  {
    refreshPanel();
    getBackgroundWindow().setAnimal(animal);
  }
  public void penUp()
  {
    penDown = false;
  }
  public void penDown()
  {
    penDown = true;
  }
  public void print(String string)
  {
    // TODO Auto-generated method stub
  }
  public void hide()
  {
    hidden = true;
  }
  public boolean isHidden()
  {
    return hidden;
  }
}
