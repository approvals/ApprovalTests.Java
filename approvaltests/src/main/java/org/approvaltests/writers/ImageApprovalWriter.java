package org.approvaltests.writers;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.approvaltests.core.ApprovalWriter;

import com.spun.util.ObjectUtils;

public class ImageApprovalWriter implements ApprovalWriter
{
  private final BufferedImage image;
  public ImageApprovalWriter(BufferedImage image)
  {
    this.image = image;
  }
  @Override
  public File writeReceivedFile(File received)
  {
    ObjectUtils.throwAsError(() -> ImageIO.write(image, "png", received));
    return received;
  }
  @Override
  public String getApprovalFilename(String base)
  {
    return base + Writer.approved + getFileExtensionWithDot();
  }
  @Override
  public String getReceivedFilename(String base)
  {
    return base + Writer.received + getFileExtensionWithDot();
  }
  @Override
  public String getFileExtensionWithDot()
  {
    return ".png";
  }
}
