package com.spun.util;

import com.spun.util.WindowUtils;
import javax.swing.*;
import java.awt.*;

/**
 * <i>Copyright 1998 American Teleconferencing Services</i>
 * <br><B>Class Description</B>
 * <br>The is the main Res Frame object
 * <br>
 * <br><B>API History</B>
 * <br>
 * <br><B>Revision Information</B>
 * <PRE>
 * $Author:: Lfalco                                           $
 * $Archive:: /Code/com/ats/client/res/ProgessFrame.java      $
 * $Date:: 8/24/99 3:49a                                      $
 * $Revision:: 7                                              $
 * </PRE>
 *
 */
public class ProgessFrame extends JDialog
{
  private JLabel       progressLabel = null;
  private JProgressBar progressBar   = null;
  private int          maxSteps      = 0;
  private JPanel       panel         = null;
  private boolean      forcePainting = true;
  private long         startTime;
  /**************************************************************************/
  public ProgessFrame(Frame frame, String title, String initLabel, int maxSteps)
  {
    this(frame, title, initLabel, maxSteps, true);
  }
  /**************************************************************************/
  public ProgessFrame(Frame frame, String title, String initLabel, int maxSteps, boolean forcePainting)
  {
    super(frame, title, false);
    this.maxSteps = maxSteps;
    this.forcePainting = forcePainting;
    init(initLabel);
  }
  /**************************************************************************/
  private void init(String initLabel)
  {
    panel = new JPanel();
    panel.setLayout(null);
    panel.setPreferredSize(new Dimension(400, 100));
    progressLabel = new JLabel(initLabel);
    progressLabel.setFont(new java.awt.Font("dialog", java.awt.Font.BOLD, 12));
    progressLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    progressLabel.setForeground(new java.awt.Color(0, 0, 156));
    progressLabel.setBounds(30, 20, 340, 20);
    panel.add(progressLabel);
    progressBar = new JProgressBar(0, maxSteps);
    progressBar.setStringPainted(true);
    progressLabel.setLabelFor(progressBar);
    progressBar.setAlignmentX(CENTER_ALIGNMENT);
    progressBar.getAccessibleContext().setAccessibleName("SwingSet loading progress");
    progressBar.setBounds(30, 40, 340, 20);
    panel.add(progressBar);
    // show the frame
    this.setContentPane(panel);
    this.pack();
    this.setVisible(true);
    WindowUtils.centerWindow(this);
    setState(initLabel, 0);
  }
  /**************************************************************************/
  public void setState(String label, int progress)
  {
    progressLabel.setText(label);
    progressBar.setValue(progress);
    if (forcePainting)
    {
      panel.paintImmediately(panel.getVisibleRect());
    }
    else
    {
      repaint();
    }
    if (progress == maxSteps)
    {
      this.dispose();
    }
  }
  /**************************************************************************/
  public void start()
  {
    this.startTime = System.currentTimeMillis();
  }
  /**************************************************************************/
  public void setStateWithTime(int progress)
  {
    long time = (System.currentTimeMillis() - startTime);
    double speed = (progress + 1) / (double) time;
    long totalTime = (long) (this.maxSteps / speed);
    long remainingTime = totalTime - time;
    String label = String.format("%s / %s - %s remaining", progress, maxSteps, new DateDifference(remainingTime)
        .getStandardTimeText(2));
    setState(label, progress);
  }
  /**************************************************************************/
  public void setFinished()
  {
    setState("done", maxSteps);
  }
  /**************************************************************************/
  /**************************************************************************/
}