package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.inline.InlineComparator;
import org.approvaltests.reporters.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnabledOnJre({JRE.JAVA_21})
public class InlineApprovalsTest
{
  @Test
  @UseReporter(DiffMergeReporter.class)
  public void testWithBuiltinReporter()
  {
    var expected = """
        Hello There***
        """;
    Options inline =
        // begin-snippet: inline_approvals
        new Options().inline(expected);
    // end-snippet
    Approvals.verify("Hello There***", inline);
  }
  @UseReporter(DiffMergeReporter.class)
  @Test
  public void testWithSpecificReporter()
  {
    var expected = """
        Hello Lada***
        """;
    Options inline = new Options().inline(expected).withReporter(DiffMergeReporter.INSTANCE);
    Approvals.verify("Hello Lada***", inline);
  }
  @Test
  public void testCreateReceivedFileText()
  {
    var inputs = List.of("""
              @Test
              public void testyMctest() {
                var expected = ""\"
                    Hello World***
                    ""\";
                Approvals.verify("", Options.inline(expected));
              }
        """, """
             @Test
             public void testyMctest()
             {
               Approvals.verify("", Options.inline(expected));
             }
        """, """
             @Test
             public void testyMctest()
             {
               var expected = ""\"
                   Hello World***
                   ""\";
               Approvals.verify("", Options.inline(expected));
             }
        """, """
              @Test
              public void testyMctest(int foo) {
                var expected = ""\"
                    Hello World***
                    ""\";
                Approvals.verify("", Options.inline(expected));
              }
        """);
    Approvals.verifyAll("Substitution", inputs, i -> "******\n" + i + "\nBecomes:\n"
        + InlineComparator.createNewReceivedFileText(i, "1\n2", "testyMctest"));
  }
  @Test
  @UseReporter(QuietReporter.class)
  public void testReportingCode()
  {
    Options inlineWithCode = new Options().inline("", true);
    Options inlineNoCode = new Options().inline("", false);
    var resultWithCode = inlineWithCode.getReporter();
    assertEquals(InlineComparator.class, resultWithCode.getClass());
    assertEquals(QuietReporter.class,
        ((FirstWorkingReporter) inlineNoCode.getReporter()).getReporters()[1].getClass());
  }
}
