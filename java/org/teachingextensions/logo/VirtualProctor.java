package org.teachingextensions.logo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.approvaltests.writers.ComponentApprovalWriter;

import com.spun.util.ObjectUtils;

public class VirtualProctor extends WindowAdapter
{
  @Override
  public void windowClosing(WindowEvent e)
  {
    try
    {
      //re-size image to 200 by 150
      String filename = "C:\\temp\\VirtualProctor.png";
      BufferedImage image = ComponentApprovalWriter.drawComponent(e.getComponent());
      //ImageIO.write(image, "png", new File(filename));
      //TestUtils.displayFile(filename);
      String urlFormat = "http://virtualproctor-tkp.appspot.com/org.teachingkidsprogramming.virtualproctor.UploadImageRack?fileName=%s.png";
      URL url = new URL(String.format(urlFormat, "test"));
      URLConnection connection = url.openConnection();
      connection.setDoOutput(true);
      connection.setDoInput(true);
      OutputStream outputStream = connection.getOutputStream();
      ImageIO.write(image, "png", outputStream);
      outputStream.close();
      connection.connect();
      Object content = connection.getContent();
      System.out.println("wrote " + url.toString() + " " + content);
    }
    catch (Exception e1)
    {
      ObjectUtils.throwAsError(e1);
    }
  }
}
