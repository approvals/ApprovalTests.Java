package org.approvaltests.awt;

import java.time.Duration;

import org.approvaltests.core.Options;
import org.approvaltests.reporters.FileCaptureReporter;
import org.approvaltests.reporters.ImageWebReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

import com.spun.util.Tuple;

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
    final CustomPanel panel = new CustomPanel(true, 20);
    AwtApprovals.verify(panel, new Options(new FileCaptureReporter()));
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
    AwtApprovals.verifySequence(squareDrawer,4, f -> squareDrawer.setSquareSize(f * 10+1));
    squareDrawer.setSquareSize(80);
    AwtApprovals.verifySequence(squareDrawer, 4, Duration.ofMillis(500), f1 -> squareDrawer.setSquareSize(f1 * 10+1));
  }
  @Test
  @UseReporter(ImageWebReporter.class)
  void testSequenceWithTimings()
  {
    SquareDrawer squareDrawer = new SquareDrawer();
    AwtApprovals.verifySequenceWithTimings(5,
        f -> new Tuple<>(squareDrawer.setSquareSize(f * 10), Duration.ofSeconds(1 + f)));
  }
}
