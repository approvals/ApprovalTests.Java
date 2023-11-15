package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.inline.InlineComparator;
import org.approvaltests.reporters.DiffMergeReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.BeyondCompareReporter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InlineApprovalsTest
{
  @Test
  public void testWithBuiltinReporter()
  {
    var expected = """
        Hello Lada***
        """;
    Options inline = Options.inline(expected);
    Approvals.verify("Hello Lada***", inline);
    assertEquals(0, ((InlineComparator) inline.getComparator()).fileWrites);
  }
  @UseReporter(DiffMergeReporter.class)
  @Test
  public void testWithSpecificReporter()
  {
    var expected = """
        Hello Lada***
        """;
    Options inline = Options.inline(expected).withReporter(DiffMergeReporter.INSTANCE);
    Approvals.verify("Hello Lada***", inline);
    assertEquals(0, ((InlineComparator) inline.getComparator()).fileWrites);
  }
  //Does this work with annotations on tests
  //Concatenating strings
  //There's some code before verify that is not replacing the result
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
    Approvals.verifyAll("Substitution", inputs,
        i -> InlineComparator.createNewReceivedFileText(i, "1\n2", "testyMctest"));
  }
}
