package org.approvaltests.awt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.writers.PaintableApprovalWriter;
import org.lambda.functions.Function1;

import com.spun.swing.Paintable;

public class PaintableMultiframeWriter implements ApprovalWriter
{
  private int                           numberOfFrames;
  private Function1<Integer, Paintable> frameGetter;
  public PaintableMultiframeWriter(int numberOfFrames, Function1<Integer, Paintable> frameGetter)
  {
    this.numberOfFrames = numberOfFrames;
    this.frameGetter = frameGetter;
  }
  @Override
  public File writeReceivedFile(File received)
  {
    return GifSequenceWriter.writeAnimatedGif(received, getBufferedImages(), 500);
  }
  private ArrayList<BufferedImage> getBufferedImages()
  {
    ArrayList<BufferedImage> images = new ArrayList<>();
    for (int i = 0; i < numberOfFrames; i++)
    {
      BufferedImage image = PaintableApprovalWriter.drawComponent(frameGetter.call(i));
      images.add(image);
    }
    return images;
  }
  @Override
  public String getFileExtensionWithDot()
  {
    return ".gif";
  }
}
