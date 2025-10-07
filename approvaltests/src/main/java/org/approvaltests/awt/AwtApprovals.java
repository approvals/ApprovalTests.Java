package org.approvaltests.awt;

import com.spun.swing.Paintable;
import com.spun.util.Tuple;
import com.spun.util.images.ImageWriter;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.writers.ComponentApprovalWriter;
import org.approvaltests.writers.DefaultApprovalWriterFactory;
import org.approvaltests.writers.ImageApprovalWriter;
import org.approvaltests.writers.PaintableApprovalWriter;
import org.lambda.functions.Function1;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.time.Duration;

public class AwtApprovals
{
  static
  {
    DefaultApprovalWriterFactory.addDefault(c -> c instanceof Component,
        (Object c, Options o) -> new ComponentApprovalWriter((Component) c));
    DefaultApprovalWriterFactory.addDefault(o -> o instanceof Paintable,
        (c, o) -> new PaintableApprovalWriter((Paintable) c));
    DefaultApprovalWriterFactory.addDefault(o -> o instanceof PaintableMultiFrame,
        (c, o) -> new PaintableMultiframeWriter((PaintableMultiFrame) c));
  }
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
    verify(bufferedImage, new Options());
  }

  public static void verify(BufferedImage bufferedImage, Options options)
  {
    Approvals.verify(new ImageApprovalWriter(bufferedImage), options.and(ImageApprovalWriter::asJreAware));
  }

  public static void verify(Component c)
  {
    verify(c, new Options());
  }

  public static void verify(Component c, Options options)
  {
    Approvals.verify(new ComponentApprovalWriter(c), options.and(Approvals.NAMES::asOsSpecificTest));
  }

  public static void verify(Paintable c)
  {
    verify(c, new Options());
  }

  public static void verify(Paintable c, Options options)
  {
    Approvals.verify(options.createWriter(c), options.and(ImageApprovalWriter::asJreAware));
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
    verifySequence(numberOfFrames, Duration.ofMillis(500), sequenceRenderer, options);
  }

  public static void verifySequence(int numberOfFrames, Duration duration,
      Function1<Integer, Paintable> sequenceRenderer)
  {
    verifySequence(numberOfFrames, duration, sequenceRenderer, new Options());
  }

  public static void verifySequence(int numberOfFrames, Duration duration,
      Function1<Integer, Paintable> sequenceRenderer, Options options)
  {
    Approvals.verify(
        options.createWriter(
            new PaintableMultiFrame(numberOfFrames, c -> new Tuple<>(sequenceRenderer.call(c), duration))),
        options);
  }

  public static void verifySequence(Paintable initialFrame, int numberOfFrames,
      Function1<Integer, Paintable> sequenceRenderer)
  {
    verifySequence(initialFrame, numberOfFrames, sequenceRenderer, new Options());
  }

  public static void verifySequence(Paintable initialFrame, int numberOfFrames,
      Function1<Integer, Paintable> sequenceRenderer, Options options)
  {
    verifySequence(initialFrame, numberOfFrames, Duration.ofMillis(500), sequenceRenderer, options);
  }

  public static void verifySequence(Paintable initialFrame, int numberOfFrames, Duration duration,
      Function1<Integer, Paintable> sequenceRenderer)
  {
    verifySequence(initialFrame, numberOfFrames, duration, sequenceRenderer, new Options());
  }

  public static void verifySequence(Paintable initialFrame, int numberOfFrames, Duration duration,
      Function1<Integer, Paintable> sequenceRenderer, Options options)
  {
    verifySequence(numberOfFrames + 1, duration, n -> {
      if (n == 0)
      {
        return initialFrame;
      }
      else
      {
        return sequenceRenderer.call(n - 1);
      }
    }, options);
  }
}
