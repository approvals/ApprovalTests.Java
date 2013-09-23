package org.teachingextensions.logo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.spun.util.MySystem;

public class VirtualProctorFileSystem extends WindowAdapter
{
  @Override
  public void windowClosing(WindowEvent event)
  {
    BufferedImage scaled = ScreenCapture.getScaledImageOf(event.getComponent(), 200, 150);
    sendImageToDisk(scaled);
  }
  public void sendImageToDisk(BufferedImage image)
  {
    try
    {
      String filename = "C:\\temp\\VirtualProctor.png";
      ImageIO.write(image, "png", new File(filename));
      //TestUtils.displayFile(filename);
    }
    catch (Exception e)
    {
      MySystem.warning(e);
    }
  }
}
