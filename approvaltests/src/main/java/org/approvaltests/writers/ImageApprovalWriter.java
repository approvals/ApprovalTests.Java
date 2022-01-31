package org.approvaltests.writers;

import com.spun.util.ObjectUtils;
import org.approvaltests.core.ApprovalWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

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
  public String getFileExtensionWithDot()
  {
    return ".png";
  }
}
