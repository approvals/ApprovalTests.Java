package org.counterdisplay;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.spun.swing.SwingUtils;
import com.spun.util.Colors;
import com.spun.util.WindowUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;

public class CounterDisplay extends JPanel
{
  private File file;
  public CounterDisplay(String fileName)
  {
    file = new File(fileName);
    SimpleLogger.variable("Reading from", file.getAbsolutePath());
    this.setPreferredSize(new Dimension(100, 100));
  }
  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    String text = FileUtils.readFile(file, "0");
    g.setColor(Colors.Greens.ForestGreen);
    g.setFont(new Font(Font.SERIF, Font.BOLD, 80));
    SwingUtils.drawCenteredString(g, text, getWidth() / 2, getHeight() / 2);
    repaint(800);
  }
  public static void main(String[] args)
  {
    String fileName = (0 < args.length) ? args[0] : "counter.txt";
    JFrame window = new JFrame("Testing Frame");
    window.setAlwaysOnTop(true);
    window.getContentPane().add(new CounterDisplay(fileName));
    WindowUtils.testFrame(window, true);
  }
}
