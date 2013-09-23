package org.teachingextensions.windows;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

public class LeftClickMouseAdapter implements MouseListener
{
  private final MouseLeftClickListener listener;
  public LeftClickMouseAdapter(MouseLeftClickListener listener)
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
    if (SwingUtilities.isLeftMouseButton(e))
    {
      listener.onLeftMouseClick(e.getX(), e.getY());
    }
  }
}
