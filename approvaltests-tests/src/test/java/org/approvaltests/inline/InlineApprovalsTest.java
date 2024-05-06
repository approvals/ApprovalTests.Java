package org.approvaltests.inline;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.AutoApproveReporter;
import org.approvaltests.reporters.DiffMergeReporter;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;
import org.lambda.actions.Action1;
import org.lambda.utils.Mutable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
          public void testyMctest () {
            var expected = ""\"
                Hello World***
                ""\";
            Approvals.verify("", Options.inline(expected));
          }
        """, """
        \t@Test
        \tpublic void testyMctest()
        \t{
        \t\tApprovals.verify("", Options.inline(expected));
        \t}
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
        """, """
          @Test
          void testyMctest() {
              Customer customer = Customer.create()
                  .existingCustomer()
                  .speakingEnglish()
                  .build();
              stateService.save(TestUtils.userId().build(), customer);
              var expected = ""\"
                  [Customer]: account lookup
                  [     Bot]: Hi there!
                  [     Bot]: Let me try to help.
                  [     Bot]: routes to '12345'
                  ""\";
              verifyConversation(expected, "account lookup");
          }
        """);
    Approvals.verifyAll("Substitution", inputs, i -> "******\n" + i + "\nBecomes:\n"
        + InlineJavaReporter.createNewReceivedFileText(i, "1\n2", "testyMctest"));
  }
  @Test
  @UseReporter(QuietReporter.class)
  public void testReportingCode()
  {
    Options inlineWithCode = new Options().inline("", InlineOptions.showCode(true));
    Options inlineNoCode = new Options().inline("", InlineOptions.showCode(false));
    var resultWithCode = inlineWithCode.getReporter();
    assertEquals(InlineJavaReporter.class, resultWithCode.getClass());
    assertEquals(QuietReporter.class,
        ((FirstWorkingReporter) inlineNoCode.getReporter()).getReporters()[1].getClass());
  }
  @Test
  void testEmptyLineAtTheEnd()
  {
    var expected = """
        Jeff Jeffty Jeff
        born on Jeffteen of Jeff, Nineteen-eighty-Jeff

        """;
    Approvals.verify(greet("Jeff"), new Options().inline(expected, InlineOptions.automatic()));
  }
  private String greet(String name)
  {
    return """
        %s %sty %s
        born on %steen of %s, Nineteen-eighty-%s

        """.formatted(name, name, name, name, name, name);
  }
  @Test
  void testSemiAutomatic()
  {
    var expected = """
        hello Lars
        ***** DELETE ME TO APPROVE *****
        """;
    Options options = new Options().inline("", InlineOptions.semiAutomatic());
    Mutable<String> result = hijackInlineReporter(options);
    Action1<Throwable> assertion = e -> assertEquals(expected, result.get());
    assertApprovalFailure("hello Lars", options, assertion);
  }
  @Test
  void testAutomatic()
  {
    var expected = """
        hello Oskar
        """;
    Options options = new Options().inline("", InlineOptions.automatic());
    assertApprovalFailure("hello Oskar", options,
        e -> assertEquals(expected, hijackInlineReporter(options).get()));
  }
  private static void assertApprovalFailure(String actual, Options options, Action1<Throwable> azzert)
  {
    boolean failed = true;
    try
    {
      Approvals.verify(actual, options);
      failed = false;
    }
    catch (Throwable t)
    {
      azzert.call(t);
    }
    assertTrue(failed, "Approval should have failed");
  }
  private static Mutable<String> hijackInlineReporter(Options options)
  {
    InlineJavaReporter reporter = (InlineJavaReporter) options.getReporter();
    assertEquals(reporter.reporter.getClass(), AutoApproveReporter.class);
    reporter.reporter = new QuietReporter();
    Mutable<String> result = new Mutable<>("");
    reporter.createNewReceivedFileText = (s, a, m) -> result.set(a);
    return result;
  }
}
