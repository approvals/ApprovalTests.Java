package org.teachingextensions.logo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VirtualProctor extends WindowAdapter
{
  @Override
  public void windowClosing(WindowEvent e)
  {
    System.out.println("VirtualProctor");
  }
}
