package com.spun.util.images;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
  * An Interface for graphic objects
  **/
public class FileImage
  implements ImageObject
{
  
  private int id;
  /***********************************************************************/
  /**
    * 
    **/
  public FileImage(byte[] bytes)
  {
    this.id = ImageWriter.getImageId();
  }
  /***********************************************************************/
  public void write(OutputStream out)
  {
    new ImageWriter(this, out, ImageWriter.Encoding.GIF).start();
  }
  /***********************************************************************/
  /**
    * 
    **/
  public int getId()
  {
    return id;
  }
  /***********************************************************************/
  /**
    *
    **/
  public BufferedImage render()
  {
    throw new Error("Method not implemented yet.");
  }
  
  
  /***********************************************************************/
  /***********************************************************************/
}