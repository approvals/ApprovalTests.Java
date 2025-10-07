package org.approvaltests.awt;

import com.spun.swing.Paintable;
import com.spun.util.Tuple;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.writers.PaintableApprovalWriter;
import org.lambda.functions.Function1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;

public class PaintableMultiframeWriter implements ApprovalWriter
{
  private int                                            numberOfFrames;
  private Function1<Integer, Tuple<Paintable, Duration>> frameGetter;
  public PaintableMultiframeWriter(PaintableMultiFrame paintableMultiFrame)
  {
    this.numberOfFrames = paintableMultiFrame.getNumberOfFrames();
    this.frameGetter = paintableMultiFrame.getFrameGetter();
  }

  @Override
  public File writeReceivedFile(File received)
  {
    return GifSequenceWriter.writeAnimatedGif(received, getBufferedImages());
  }

  private ArrayList<Tuple<BufferedImage, Duration>> getBufferedImages()
  {
    ArrayList<Tuple<BufferedImage, Duration>> images = new ArrayList<>();
    for (int i = 0; i < numberOfFrames; i++)
    {
      final Tuple<Paintable, Duration> tuple = frameGetter.call(i);
      BufferedImage image = PaintableApprovalWriter.drawComponent(tuple.getFirst());
      images.add(new Tuple<>(image, tuple.getSecond()));
    }
    return images;
  }

  @Override
  public String getFileExtensionWithDot()
  {
    return ".gif";
  }
}
