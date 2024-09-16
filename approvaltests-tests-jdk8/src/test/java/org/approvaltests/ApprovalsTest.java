package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.EnvironmentVariableReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.BeyondCompareReporter;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.assertThrows;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class ApprovalsTest
{
  @Test
  public void testToString()
  {
    Rectangle objectUnderTest = new Rectangle(5, 10, 100, 200);
    Options options = new Options().withReporter(EnvironmentVariableReporter.INSTANCE);
    Approvals.verify(objectUnderTest, options);
  }
  @Test
  public void testAsJson()
  {
    JsonApprovals.verifyAsJson(new Rectangle(5, 10, 100, 200));
  }
  @Test
  void verifyAllTemplate()
  {
    String[] inputs = {"input.value1", "input.value2"};
    Approvals.verifyAll("TITLE", inputs, s -> "placeholder " + s);
  }
  @Test
  void testTwoVerify()
  {
    Approvals.verify("one");
    assertThrows(ApprovalsDuplicateVerifyException.class, () -> Approvals.verify("two"));
  }
}
