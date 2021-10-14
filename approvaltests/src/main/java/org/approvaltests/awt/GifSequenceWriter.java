package org.approvaltests.awt;
//  GifSequenceWriter.java

//
//  Created by Elliot Kroo on 2009-04-25.
//
// This work is licensed under the Creative Commons Attribution 3.0 Unported
// License. To view a copy of this license, visit
// http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
// Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
import com.spun.util.Tuple;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import javax.imageio.IIOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import com.spun.util.ObjectUtils;

public class GifSequenceWriter implements AutoCloseable
{
  private final int         imageType;
  private final Duration    timeBetweenFrames;
  private final boolean     loopContinuously;
  protected ImageWriter     gifWriter;
  protected ImageWriteParam imageWriteParam;
  /**
   * Creates a new GifSequenceWriter
   *
   * @param outputStream the ImageOutputStream to be written to
   * @param imageType one of the imageTypes specified in BufferedImage
   * @param timeBetweenFrames the time between frames in miliseconds
   * @param loopContinuously wether the gif should loop repeatedly
   * @throws IIOException if no gif ImageWriters are found
   *
   * @author Elliot Kroo (elliot[at]kroo[dot]net)
   */
  public GifSequenceWriter(ImageOutputStream outputStream, int imageType, Duration timeBetweenFrames,
      boolean loopContinuously)
  {
    this.imageType = imageType;
    this.timeBetweenFrames = timeBetweenFrames;
    this.loopContinuously = loopContinuously;
    gifWriter = getWriter();
    imageWriteParam = gifWriter.getDefaultWriteParam();
    gifWriter.setOutput(outputStream);
    ObjectUtils.throwAsError(() -> gifWriter.prepareWriteSequence(null));
  }
  private IIOMetadata getMetadata(Duration timeBetweenFramesMS)
  {
    ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
    IIOMetadata imageMetaData2 = gifWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);
    String metaFormatName = imageMetaData2.getNativeMetadataFormatName();
    IIOMetadataNode root = (IIOMetadataNode) imageMetaData2.getAsTree(metaFormatName);
    IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
    graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
    graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
    graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
    graphicsControlExtensionNode.setAttribute("delayTime", "" + timeBetweenFramesMS.toMillis() / 10);
    graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");
    IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
    commentsNode.setAttribute("CommentExtension", "Created by MAH");
    IIOMetadataNode appEntensionsNode = getNode(root, "ApplicationExtensions");
    IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
    child.setAttribute("applicationID", "NETSCAPE");
    child.setAttribute("authenticationCode", "2.0");
    child.setUserObject(getBytesForLoopContinuously(loopContinuously));
    appEntensionsNode.appendChild(child);
    ObjectUtils.throwAsError(() -> imageMetaData2.setFromTree(metaFormatName, root));
    return imageMetaData2;
  }
  public static byte[] getBytesForLoopContinuously(boolean loopContinuously)
  {
    byte[] loop = {1, 0, 0};
    byte[] once = {1, 1, 0};
    return loopContinuously ? loop : once;
  }
  static File writeAnimatedGif(File imageFile, List<Tuple<BufferedImage, Duration>> images)
  {
    try (ImageOutputStream output = new FileImageOutputStream(imageFile))
    {
      try (GifSequenceWriter writer = new GifSequenceWriter(output, images.get(0).getFirst().getType(),
          Duration.ZERO, true))
      {
        for (Tuple<BufferedImage, Duration> image : images)
        {
          writer.writeToSequence(image.getFirst(), image.getSecond());
        }
      }
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
    return imageFile;
  }
  public void writeToSequence(RenderedImage img, Duration timeBetweenFrames)
  {
    ObjectUtils.throwAsError(
        () -> gifWriter.writeToSequence(new IIOImage(img, null, getMetadata(timeBetweenFrames)), imageWriteParam));
  }
  /**
   * Close this GifSequenceWriter object. This does not close the underlying
   * stream, just finishes off the GIF.
   */
  public void close()
  {
    ObjectUtils.throwAsError(() -> gifWriter.endWriteSequence());
  }
  /**
   * Returns the first available GIF ImageWriter using
   * ImageIO.getImageWritersBySuffix("gif").
   *
   * @return a GIF ImageWriter object
   * @throws IIOException if no GIF image writers are returned
   */
  private static ImageWriter getWriter()
  {
    Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix("gif");
    if (!iter.hasNext())
    {
      throw new RuntimeException("No GIF Image Writers Exist");
    }
    else
    {
      return iter.next();
    }
  }
  /**
   * Returns an existing child node, or creates and returns a new child node (if
   * the requested node does not exist).
   *
   * @param rootNode the <tt>IIOMetadataNode</tt> to search for the child node.
   * @param nodeName the name of the child node.
   *
   * @return the child node, if found or a new node created with the given name.
   */
  private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName)
  {
    int nNodes = rootNode.getLength();
    for (int i = 0; i < nNodes; i++)
    {
      if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName) == 0)
      { return ((IIOMetadataNode) rootNode.item(i)); }
    }
    IIOMetadataNode node = new IIOMetadataNode(nodeName);
    rootNode.appendChild(node);
    return node;
  }
}
