package org.approvaltests.inline;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.DiffMergeReporter;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    Approvals.verify(greet("Jeff"), new Options().inline(expected));
  }
  private String greet(String name)
  {
    return """
        %s %sty %s
        born on %steen of %s, Nineteen-eighty-%s

        """.formatted(name, name, name, name, name, name);
  }
}
