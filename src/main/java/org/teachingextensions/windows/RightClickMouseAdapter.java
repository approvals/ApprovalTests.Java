package org.teachingextensions.windows;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

public class RightClickMouseAdapter implements MouseListener
{
  private final MouseRightClickListener listener;
  public RightClickMouseAdapter(MouseRightClickListener listener)
  {
    this.listener = listener;
  }
  @Override
  public void mouseClicked(MouseEvent e)
  {
  }
  @Override
  public void mouseEntered(MouseEvent e)
  {
  }
  @Override
  public void mouseExited(MouseEvent e)
  {
  }
  @Override
  public void mousePressed(MouseEvent e)
  {
  }
  @Override
  public void mouseReleased(MouseEvent e)
  {
    if (SwingUtilities.isRightMouseButton(e))
    {
      listener.onRightMouseClick(e.getX(), e.getY());
    }
  }
}
