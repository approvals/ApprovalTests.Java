package com.spun.util.images;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import Acme.JPM.Encoders.GifEncoder;

import com.spun.util.MySystem;

/**
 * An Interface for graphic objects
 **/
public class ImageWriter
{
  public enum Encoding {
    JPEG, GIF
  }
  private OutputStream  out   = null;
  private ImageObject   image = null;
  private Encoding      type  = null;
  private static Random rand  = new Random();
  private static int    index = rand.nextInt();
  /***********************************************************************/
  public ImageWriter(ImageObject image, OutputStream out, Encoding type)
  {
    this.image = image;
    this.out = out;
    this.type = type;
  }
  public static void writeImage(BufferedImage image, OutputStream out, Encoding type)
  {
    new ImageWriter(new BufferedImageObject(image), out, type).start();
  }
  public static BufferedImage toBufferedImage(Image image)
  {
    BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
    Graphics g = bimage.createGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();
    return bimage;
  }
  /***********************************************************************/
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
      MySystem.warning(e);
    }
  }
  /***********************************************************************/
  private void encodeJPEG() throws IOException
  {
    BufferedImage bi = image.render();
    ImageIO.write(bi, "jpg", out);
  }
  /***********************************************************************/
  private void encodeGIF() throws IOException
  {
    MySystem.variable("making GIF");
    BufferedImage bi = image.render();
    GifEncoder encoder = new GifEncoder(bi, out, true);
    encoder.encode();
  }
  /***********************************************************************/
  public static synchronized int getImageId()
  {
    return (index++);
  }
  /***********************************************************************/
  /***********************************************************************/
  public static class BufferedImageObject implements ImageObject
  {
    private BufferedImage image;
    public BufferedImageObject(BufferedImage image)
    {
      this.image = image;
    }
    public int getId()
    {
      return 0;
    }
    public BufferedImage render()
    {
      return image;
    }
    public void write(OutputStream out)
    {
    }
  }
}