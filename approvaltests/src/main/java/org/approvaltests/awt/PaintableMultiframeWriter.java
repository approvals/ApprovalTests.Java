package org.approvaltests.awt;

import com.spun.swing.Paintable;
import com.spun.util.ObjectUtils;
import com.spun.util.persistence.ExecutableQuery;
import org.approvaltests.approvers.ApprovalApprover;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.writers.PaintableApprovalWriter;
import org.lambda.functions.Function1;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

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
    try
    {
      ArrayList<BufferedImage> images = getBufferedImages();
      try (ImageOutputStream output = new FileImageOutputStream(received))
      {
        try (GifSequenceWriter writer = new GifSequenceWriter(output, images.get(0).getType(), 500, true))
        {
          for (BufferedImage image : images)
          {
            writer.writeToSequence(image);
          }
        }
      }
      return received;
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
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