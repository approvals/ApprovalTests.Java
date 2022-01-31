package org.counterdisplay;

import com.spun.swing.SwingUtils;
import com.spun.util.Colors;
import com.spun.util.WindowUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

public class CounterDisplay extends JPanel
{
  private static final long serialVersionUID = 1L;
  private File              successfile;
  private File              failureFile;
  public CounterDisplay(String successFile, String failureFile)
  {
    this.successfile = new File(successFile);
    this.failureFile = new File(failureFile);
    SimpleLogger.variable("Reading from", successfile.getAbsolutePath());
    this.setPreferredSize(new Dimension(200, 100));
  }
  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Colors.Greens.ForestGreen);
    g.fillRect(0, 0, 100, 100);
    g.setColor(Colors.Reds.Red);
    g.fillRect(101, 0, 100, 100);
    String sucessCount = FileUtils.readFile(successfile, "0").trim();
    String failureCount = FileUtils.readFile(failureFile, "0").trim();
    g.setColor(Colors.Whites.GhostWhite);
    g.setFont(new Font(Font.SERIF, Font.BOLD, 80));
    int dx = getWidth() / 4;
    SwingUtils.drawCenteredString(g, sucessCount, dx, getHeight() / 2);
    SwingUtils.drawCenteredString(g, failureCount, dx * 3, getHeight() / 2);
    repaint(800);
  }
  public static void main(String[] args)
  {
    String pass = (0 < args.length) ? args[0] : "counter_pass.txt";
    String fail = (1 < args.length) ? args[1] : "counter_fail.txt";
    JFrame window = new JFrame("Testing Frame");
    window.setAlwaysOnTop(true);
    window.getContentPane().add(new CounterDisplay(pass, fail));
    WindowUtils.testFrame(window, true);
  }
}
