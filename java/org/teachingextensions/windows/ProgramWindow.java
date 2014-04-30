package org.teachingextensions.windows;

import javax.swing.JFrame;

import org.teachingextensions.logo.TurtlePanel;
import org.teachingextensions.logo.VirtualProctorWeb;

import com.spun.util.FrameCloser;
import com.spun.util.WindowUtils;

public class ProgramWindow extends TurtlePanel
{
  public ProgramWindow(String title)
  {
    JFrame frame = new JFrame(title);
    frame.getContentPane().add(this);
    ProgramWindow.createStandardFrame(frame);
  }
  public static void createStandardFrame(JFrame frame)
  {
    WindowUtils.testFrame(frame, new VirtualProctorWeb(), new FrameCloser());
  }
}
