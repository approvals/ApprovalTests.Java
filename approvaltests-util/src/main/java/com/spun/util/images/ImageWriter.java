package com.spun.util.images;

import com.spun.util.logger.SimpleLogger;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class ImageWriter
{
  public enum Encoding {
                        JPEG, GIF
  }
  private OutputStream  out   = null;
  private BufferedImage image = null;
  private Encoding      type  = null;
  private static Random rand  = new Random();
  private static int    index = rand.nextInt();
  public ImageWriter(BufferedImage image, OutputStream out, Encoding type)
  {
    this.image = image;
    this.out = out;
    this.type = type;
  }
  public static void writeImage(BufferedImage image, OutputStream out, Encoding type)
  {
    new ImageWriter(image, out, type).start();
  }
  public static BufferedImage toBufferedImage(Image image)
  {
    BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null),
        BufferedImage.TYPE_INT_RGB);
    Graphics g = bimage.createGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();
    return bimage;
  }
  public void start()
  {
    try
    {
      switch (type)
      {
        case JPEG :
          encodeJPEG();
          break;
        case GIF :
          encodeGIF();
          break;
      }
    }
    catch (IOException e)
    {
      SimpleLogger.warning(e);
    }
  }
  private void encodeJPEG() throws IOException
  {
    ImageIO.write(image, "jpg", out);
  }
  private void encodeGIF() throws IOException
  {
    SimpleLogger.variable("making GIF");
    ImageIO.write(image, "GIF", out);
  }
  public static synchronized int getImageId()
  {
    return (index++);
  }
}
