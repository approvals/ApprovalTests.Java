package com.spun.util;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.spun.util.logger.SimpleLogger;

public class WindowUtils
{
  
  public static void centerWindow(java.awt.Window window)
  {
    Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    Dimension w = window.getSize();
    int dx = (int) w.getWidth();
    int dy = (int) w.getHeight();
    int x = (int) ((d.getWidth() - dx) / 2);
    int y = (int) ((d.getHeight() - dy) / 2);
    SimpleLogger.variable(" size (" + x + "," + y + "," + dx + "," + dy + ")");
    window.setBounds(x, y, dx, dy + 1);
  }
  
  public static void testPanel(JPanel panel)
  {
    JFrame test = new JFrame("Testing Frame");
    test.getContentPane().add(panel);
    testFrame(test, true);
  }
  
  public static void testFrame(JFrame frame)
  {
    testFrame(frame, true);
  }
  
  public static void copyToClipBoard(String code)
  {
    copyToClipBoard(code, true);
  }
  public static void copyToClipBoard(String code, boolean displayMessage)
  {
    java.awt.datatransfer.StringSelection selection = new java.awt.datatransfer.StringSelection(code);
    java.awt.Frame frame = new java.awt.Frame();
    frame.getToolkit().getSystemClipboard().setContents(selection, selection);
    if (displayMessage)
    {
      JOptionPane.showMessageDialog(null, "Code copied to Clipboard", "Finished", JOptionPane.INFORMATION_MESSAGE);
    }
    frame.dispose();
  }
  
  public static void testFrame(JFrame frame, boolean closeOnExit)
  {
    if (closeOnExit)
    {
      testFrame(frame, new FrameCloser());
    }
    else
    {
      testFrame(frame);
    }
  }
  
  public static void testFrame(JFrame frame, WindowAdapter... array)
  {
    frame.pack();
    for (WindowAdapter closer : array)
    {
      frame.addWindowListener(closer);
    }
    WindowUtils.centerWindow(frame);
    frame.setVisible(true);
  }
}