package org.approvaltests.writers;

import com.spun.util.NumberUtils;
import com.spun.util.ObjectUtils;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.namer.NamedEnvironment;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.approvaltests.namer.NamerFactory.asMachineSpecificTest;

public class ImageApprovalWriter implements ApprovalWriter
{
  private final BufferedImage image;
  public ImageApprovalWriter(BufferedImage image)
  {
    this.image = image;
  }
  public static NamedEnvironment asJreAware()
  {
    return asMachineSpecificTest(() -> getJreInformation());
  }
  private static String getJreInformation()
  {
    String javaVersion = System.getProperty("java.version");
    String majorVersion = javaVersion.split("\\.")[0];
    majorVersion = NumberUtils.stripNonNumeric(majorVersion);
    int major = Integer.parseInt(majorVersion);
    return major < 11 ? "jdkPre11" : "jdkPost11";
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
