package org.approvaltests.awt;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.time.Duration;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.writers.ComponentApprovalWriter;
import org.approvaltests.writers.ImageApprovalWriter;
import org.lambda.functions.Function1;

import com.spun.swing.Paintable;
import com.spun.util.Tuple;
import com.spun.util.images.ImageWriter;

public class AwtApprovals
{
  public static void verify(Image image)
  {
    verify(image, new Options());
  }
  public static void verify(Image image, Options options)
  {
    verify(ImageWriter.toBufferedImage(image), options);
  }
  public static void verify(BufferedImage bufferedImage)
  {
    Approvals.verify(bufferedImage, new Options());
  }
  public static void verify(BufferedImage bufferedImage, Options options)
  {
    Approvals.verify(new ImageApprovalWriter(bufferedImage), options);
  }
  public static void verify(Component c)
  {
    verify(c, new Options());
  }
  public static void verify(Component c, Options options)
  {
    try (NamedEnvironment env = NamerFactory.asOsSpecificTest())
    {
      Approvals.verify(new ComponentApprovalWriter(c), options);
    }
  }
  public static void verify(Paintable c)
  {
    verify(c, new Options());
  }
  public static void verify(Paintable c, Options options)
  {
    Approvals.verify(options.createWriter(c), options);
  }
  public static void verifySequence(int numberOfFrames, Function1<Integer, Paintable> sequenceRenderer)
  {
    verifySequence(numberOfFrames, sequenceRenderer, new Options());
  }
  public static void verifySequenceWithTimings(int numberOfFrames,
      Function1<Integer, Tuple<Paintable, Duration>> sequenceRenderer)
  {
    verifySequenceWithTimings(numberOfFrames, sequenceRenderer, new Options());
  }
  public static void verifySequenceWithTimings(int numberOfFrames,
      Function1<Integer, Tuple<Paintable, Duration>> sequenceRenderer, Options options)
  {
    Approvals.verify(options.createWriter(new PaintableMultiFrame(numberOfFrames, sequenceRenderer)), options);
  }
  public static void verifySequence(int numberOfFrames, Function1<Integer, Paintable> sequenceRenderer,
      Options options)
  {
    Approvals.verify(options.createWriter(new PaintableMultiFrame(numberOfFrames,
        c -> new Tuple<>(sequenceRenderer.call(c), Duration.ofMillis(500)))), options);
  }
}
