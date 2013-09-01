package org.teachingextensions.logo;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.approvaltests.writers.ComponentApprovalWriter;

public class ScreenCapture
{
  public static BufferedImage getScaledImageOf(Component component, int width, int height)
  {
    BufferedImage image = ComponentApprovalWriter.drawComponent(component);
    return scaleImage(image, width, height);
  }
  public static BufferedImage scaleImage(BufferedImage image, int width, int height)
  {
    return toBufferedImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
  }
  public static BufferedImage toBufferedImage(Image image)
  {
    if (image instanceof BufferedImage) { return (BufferedImage) image; }
    BufferedImage buffered = new BufferedImage(image.getWidth(null), image.getHeight(null),
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = buffered.createGraphics();
    graphics.drawImage(image, 0, 0, null);
    graphics.dispose();
    return buffered;
  }
}
