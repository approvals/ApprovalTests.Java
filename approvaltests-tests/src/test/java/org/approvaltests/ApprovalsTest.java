package org.approvaltests;

import com.spun.util.io.FileUtils;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.*;
import org.approvaltests.reporters.ReportWithBeyondCompare;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.StringReader;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class ApprovalsTest
{
  @Test
  public void testToString()
  {
    Rectangle objectUnderTest = new Rectangle(5, 10, 100, 200);
    // begin-snippet: configure_reporter_with_options
    Options options = new Options().withReporter(ReportWithBeyondCompare.INSTANCE);
    Approvals.verify(objectUnderTest, options);
    // end-snippet
  }

  @Test
  public void testAsJson()
  {
    JsonApprovals.verifyAsJson(new Rectangle(5, 10, 100, 200));
  }

  @Test
  public void testWrongCall()
  {
    Approvals.verifyException(() -> {
      JButton b = new JButton("Approval Tests Rule");
      b.setPreferredSize(new Dimension(150, 20));
      Approvals.verify(b, new Options(new ReportNothing()));
    });
  }

  @Test
  void verifyAllTemplate()
  {
    // begin-snippet: VerifyAllStartingPoint
    String[] inputs = {"input.value1", "input.value2"};
    Approvals.verifyAll("TITLE", inputs, s -> "placeholder " + s);
    // end-snippet
  }

  @Test
  void testTwoVerify()
  {
    Approvals.verify("one");
    assertThrows(ApprovalsDuplicateVerifyException.class, () -> Approvals.verify("two"));
  }

  @Test
  void verifyPath()
  {
    File file = FileUtils.saveToFile("pre_", ".txt", new StringReader("hello from Path"));
    Approvals.verify(file.toPath());
  }
}
