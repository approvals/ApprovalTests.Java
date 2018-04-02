package org.approvaltests.webpages;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WebPageChangeDetectorGui extends JPanel
{
  private static final long     serialVersionUID = 1L;
  private JLabel                urlLabel;
  public JTextField             urlTextField;
  public JButton                lock;
  public JButton                check;
  private WebPageChangeDetector controller;
  public WebPageChangeDetectorGui(WebPageChangeDetector controller)
  {
    this.controller = controller;
    initialize();
  }
  private void initialize()
  {
    this.setPreferredSize(new Dimension(400, 50));
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    urlLabel = new JLabel("URL");
    c.weightx = 0.5;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    this.add(urlLabel, c);
    urlTextField = new JTextField();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 0.5;
    c.gridwidth = 3;
    c.gridx = 1;
    c.gridy = 0;
    this.add(urlTextField, c);
    lock = new JButton("Lock");
    lock.addActionListener(controller);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 0.5;
    c.gridwidth = 1;
    c.gridx = 2;
    c.gridy = 1;
    this.add(lock, c);
    check = new JButton("Check");
    check.addActionListener(controller);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 0.5;
    c.gridwidth = 1;
    c.gridx = 3;
    c.gridy = 1;
    this.add(check, c);
  }
}
