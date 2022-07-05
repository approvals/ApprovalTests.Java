package org.approvaltests.awt;

import com.spun.util.Tuple;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.FileCaptureReporter;
import org.approvaltests.reporters.ImageWebReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.time.Duration;

//@UseReporter({FileCaptureReporter.class})
public class ApprovalsTest
{
  @DisabledOnJre({JRE.JAVA_8})
  @Test
  void customPanel()
  {
    final CustomPanel panel = new CustomPanel();
    AwtApprovals.verify(panel);
  }
  //  @DisabledOnJre({JRE.JAVA_8})
  // TODO
  @Disabled("continue here next week")
  @Test
  void customPanelWithText()
  {
    // begin-snippet: file_capture_reporter_example
    final CustomPanel panel = new CustomPanel(true, 20);
    AwtApprovals.verify(panel, new Options(new FileCaptureReporter()));
    // end-snippet
  }
  @EnabledOnJre({JRE.JAVA_8})
  @Test
  void customPanelOnJre8()
  {
    final CustomPanel panel = new CustomPanel();
    AwtApprovals.verify(panel);
  }
  @Test
  @UseReporter(ImageWebReporter.class)
  void testSequence()
  {
    // begin-snippet: SequencePaintables
    SquareDrawer squareDrawer = new SquareDrawer();
    AwtApprovals.verifySequence(5, f -> squareDrawer.setSquareSize(f * 10));
    // end-snippet
    AwtApprovals.verifySequence(5, Duration.ofMillis(500), f1 -> squareDrawer.setSquareSize(f1 * 10));
  }
  @Test
  @UseReporter(ImageWebReporter.class)
  void testSequenceWithInitialState()
  {
    SquareDrawer squareDrawer = new SquareDrawer();
    squareDrawer.setSquareSize(80);
    AwtApprovals.verifySequence(squareDrawer, 4, f -> squareDrawer.setSquareSize(f * 10 + 1));
    squareDrawer.setSquareSize(80);
    AwtApprovals.verifySequence(squareDrawer, 4, Duration.ofMillis(500),
        f1 -> squareDrawer.setSquareSize(f1 * 10 + 1));
  }
  @Test
  @UseReporter(ImageWebReporter.class)
  void testSequenceWithTimings()
  {
    SquareDrawer squareDrawer = new SquareDrawer();
    AwtApprovals.verifySequenceWithTimings(5,
        f -> new Tuple<>(squareDrawer.setSquareSize(f * 10), Duration.ofSeconds(1 + f)));
  }
  @DisabledOnJre({JRE.JAVA_8})
  @Test
  void testBufferedImage()
  {
    BufferedImage bufferedImage = new BufferedImage(60, 30, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = bufferedImage.getGraphics();
    graphics.setColor(Color.BLUE);
    graphics.fillRect(0, 0, 60, 10);
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 10, 60, 10);
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 20, 60, 10);
    AwtApprovals.verify(bufferedImage);
    AwtApprovals.verify(bufferedImage, new Options());
  }
}
