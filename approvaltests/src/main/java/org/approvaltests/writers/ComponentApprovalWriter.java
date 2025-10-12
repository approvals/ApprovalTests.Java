package org.approvaltests.writers;

import org.approvaltests.core.ApprovalWriter;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

public class ComponentApprovalWriter implements ApprovalWriter
{
  private ImageApprovalWriter image = null;
  public ComponentApprovalWriter(Component c)
  {
    BufferedImage image = drawComponent(c);
    this.image = new ImageApprovalWriter(image);
  }

  public static BufferedImage drawComponent(Component c)
  {
    validateComponent(c);
    BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.createGraphics();
    c.paint(g);
    g.dispose();
    return image;
  }

  private static void validateComponent(Component c)
  {
    if (!c.isValid())
    {
      JFrame frame = new JFrame();
      frame.getContentPane().add(c);
      frame.pack();
    }
  }

  @Override
  public File writeReceivedFile(File filename)
  {
    return image.writeReceivedFile(filename);
  }

  @Override
  public String getFileExtensionWithDot()
  {
    return image.getFileExtensionWithDot();
  }
}
