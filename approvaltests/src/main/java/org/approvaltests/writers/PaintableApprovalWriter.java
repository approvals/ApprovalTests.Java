package org.approvaltests.writers;

import com.spun.swing.Paintable;
import org.approvaltests.core.ApprovalWriter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

public class PaintableApprovalWriter implements ApprovalWriter
{
  private ImageApprovalWriter image = null;
  public PaintableApprovalWriter(Paintable paintable)
  {
    BufferedImage image = drawComponent(paintable);
    this.image = new ImageApprovalWriter(image);
  }
  public static BufferedImage drawComponent(Paintable paintable)
  {
    final Dimension size = paintable.getSize();
    BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.createGraphics();
    paintable.paint(g);
    g.dispose();
    return image;
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
