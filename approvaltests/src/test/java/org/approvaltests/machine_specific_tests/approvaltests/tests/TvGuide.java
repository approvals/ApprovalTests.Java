package org.approvaltests.machine_specific_tests.approvaltests.tests;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TvGuide extends JPanel
{
  private JList<String> times;
  public TvGuide()
  {
    setPreferredSize(new Dimension(200, 70));
    setLayout(new GridLayout(2, 2));
    times = new JList<String>(new String[]{"3pm"});
    add(new JLabel("Time :"));
    add(times);
    add(new JLabel("Shows :"));
    add(new JList<String>(new String[]{"The IT Crowd", "Futurama", "Firefly"}));
  }
  public void selectTime(String string)
  {
    times.setSelectedIndex(0);
  }
}
