package org.approvaltests.tests;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class TvGuide extends JPanel
{
  private JList times;
  public TvGuide()
  {
    setPreferredSize(new Dimension(200, 70));
    setLayout(new GridLayout(2, 2));
    times = new JList(new String[]{"3pm"});
    add(new JLabel("Time :"));
    add(times);
    add(new JLabel("Shows :"));
    add(new JList(new String[]{"The IT Crowd", "Futurama", "Firefly"}));
  }
  public void selectTime(String string)
  {
    times.setSelectedIndex(0);
  }
}
