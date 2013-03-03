package com.spun.util.images;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
  * An Interface for graphic objects
  **/
public interface ImageObject
{
  /***********************************************************************/
  public void write(OutputStream out);
  /**
    * Returns an unique id.
    **/
  public int getId();
  /***********************************************************************/
  /**
    *
    **/
  public BufferedImage render();
  /***********************************************************************/
  /***********************************************************************/
}