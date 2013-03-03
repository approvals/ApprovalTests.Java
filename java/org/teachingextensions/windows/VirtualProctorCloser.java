package org.teachingextensions.windows;

import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.teachingextensions.logo.Turtle;

import com.spun.util.MySystem;

public class VirtualProctorCloser extends WindowAdapter
{
  private final Turtle turtle;
  public VirtualProctorCloser(Turtle turtle)
  {
    this.turtle = turtle;
  }
  /***********************************************************************/
  public void windowClosing(java.awt.event.WindowEvent e)
  {
    try
    {
      String urlFormat = "http://virtualproctor-tkp.appspot.com/org.teachingkidsprogramming.virtualproctor.UploadImageRack?fileName=%s.png";
      String machineName = getComputerName();
      BufferedImage image = turtle.getImage();
      URL url = new URL(String.format(urlFormat, machineName));
      URLConnection connection = url.openConnection();
      connection.setDoOutput(true);
      connection.setDoInput(false);
      OutputStream outputStream = connection.getOutputStream();
      ImageIO.write(image, "png", outputStream);
      outputStream.close();
      connection.connect();
      System.out.println("wrote" + url.toString());
    }
    catch (Exception e2)
    {
      MySystem.warning(e2);
    }
    System.exit(0);
  }
  public String getComputerName()
  {
    return System.getenv("COMPUTERNAME");
  }
  /***********************************************************************/
  public void windowClosed(java.awt.event.WindowEvent e)
  {
    System.exit(0);
  }
}
