package org.teachingextensions.logo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.lambda.actions.Action0;

import com.spun.util.MySystem;
import com.spun.util.ThreadLauncher;
import com.spun.util.ThreadUtils;
import com.spun.util.io.FileUtils;

public class VirtualProctor extends WindowAdapter
{
  private boolean finished = false;
  @Override
  public void windowClosing(WindowEvent event)
  {
    final BufferedImage scaled = ScreenCapture.getScaledImageOf(event.getComponent(), 200, 150);
    ThreadLauncher.launch(new Action0()
    {
      @Override
      public void call()
      {
        sendImageToWeb(scaled);
        finished = true;
      }
    });
  }
  @Override
  public void windowClosed(WindowEvent e)
  {
    while (!finished)
    {
      ThreadUtils.sleep(50);
    }
  }
  public void sendImageToDisk(BufferedImage image) throws IOException
  {
    String filename = "C:\\temp\\VirtualProctor.png";
    ImageIO.write(image, "png", new File(filename));
    //TestUtils.displayFile(filename);
  }
  public void sendImageToWeb(BufferedImage image)
  {
    try
    {
      String urlFormat = "http://virtualproctor-tkp.appspot.com/org.teachingkidsprogramming.virtualproctor.UploadImageRack?fileName=%s.png";
      URL url = new URL(String.format(urlFormat, getComputerName()));
      URLConnection connection = url.openConnection();
      connection.setDoOutput(true);
      connection.setDoInput(true);
      OutputStream outputStream = connection.getOutputStream();
      ImageIO.write(image, "png", outputStream);
      outputStream.close();
      String content = FileUtils.readStream((InputStream) connection.getContent());
      MySystem.event(content);
    }
    catch (Exception e)
    {
      MySystem.warning(e);
    }
  }
  public String getComputerName()
  {
    return System.getenv("COMPUTERNAME");
  }
}
